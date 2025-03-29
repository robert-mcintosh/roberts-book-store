package com.mc.bookstore.service;

import com.mc.bookstore.config.exceptions.NotFoundException;
import com.mc.bookstore.model.beans.PurchaseRecord;
import com.mc.bookstore.model.entities.*;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.DiscountRepository;
import com.mc.bookstore.repository.PurchaseItemRepository;
import com.mc.bookstore.repository.PurchaseRepository;
import java.util.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

  private final PurchaseRepository purchaseRepository;
  private final PurchaseItemRepository purchaseItemRepository;
  private final BookRepository bookRepository;
  private final DiscountRepository discountRepository;

  private final CustomerService customerService;

  public PurchaseService(
      PurchaseRepository purchaseRepository,
      PurchaseItemRepository purchaseItemRepository,
      BookRepository bookRepository,
      DiscountRepository discountRepository,
      CustomerService customerService) {
    this.purchaseRepository = purchaseRepository;
    this.purchaseItemRepository = purchaseItemRepository;
    this.bookRepository = bookRepository;
    this.discountRepository = discountRepository;
    this.customerService = customerService;
  }

  /**
   * Retrieves the purchase record for the specified purchase ID. This method fetches the purchase
   * details and associated purchase items and converts them into a {@link PurchaseRecord}.
   *
   * @param id the ID of the purchase to retrieve
   * @return the {@link PurchaseRecord} containing the purchase details and items
   * @throws NotFoundException if the purchase or associated purchase items cannot be found
   */
  public PurchaseRecord getPurchase(Long id) {

    Purchase purchase = purchaseRepository.findById(id).orElse(null);

    if (purchase == null) throw new NotFoundException("Purchase does not exist!");
    if (purchase.getItems() == null) throw new NotFoundException("Purchase items not found!");

    // Explicitly initialize the 'items' collection
    Hibernate.initialize(purchase.getItems());

    return toPurchaseRecord(purchase);
  }

  public PurchaseRecord makePurchase(Long customerId, List<Long> bookIds) {

    // get customer info
    Customer customer = customerService.getCustomer(customerId);

    //    get books
    List<Book> books = bookRepository.findAllById(bookIds);

    // populate basics of book and save it to get a purchaseId
    PurchaseRecord purchaseRecord = new PurchaseRecord();
    purchaseRecord.setCustomerId(customer.getId());

    { // save to generate an ID for the purchase
      Purchase temporaryPurchase = purchaseRecord;
      purchaseRepository.saveAndFlush(temporaryPurchase);
      purchaseRecord.setPurchaseId(temporaryPurchase.getPurchaseId());
    }

    double totalDiscountedPrice = calculateTotalPrice(purchaseRecord, books);
    purchaseRecord.setTotalPrice(totalDiscountedPrice);

    // increment loyalty points
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() + books.size());
    customerService.updateLoyaltyPoints(customerId, customer.getLoyaltyPoints());

    purchaseRepository.saveAndFlush(purchaseRecord);
    purchaseItemRepository.saveAll(purchaseRecord.getItems());

    return purchaseRecord;
  }

  public Purchase refund(Long purchaseId) {
    Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
    if (purchase == null) throw new NotFoundException("Purchase does not exist!");

    // todo add refund logic
    return null;
  }

  private double calculateTotalPrice(PurchaseRecord purchaseRecord, List<Book> books) {
    double totalDiscountPrice = 0.0;

    // save to generate an ID for the purchase
    //    purchaseRepository.save(purchase);

    // ASSUMPTION: bulk discount is applied on cart size, not quantity of a particular type
    boolean qualifiesForBulkDiscount = books.size() >= 3;

    for (Book book : books) {
      PurchaseItem item = new PurchaseItem();
      item.setPurchaseId(purchaseRecord.getPurchaseId());
      item.setBookId(book.getId());
      item.setPrice(book.getPrice());

      Optional<Discount> discountOpt = discountRepository.findByBookType(book.getType());
      if (discountOpt.isPresent()) {
        Discount discount = discountOpt.get();
        double bookPrice = book.getPrice();

        double discountPercentage = discount.getDiscountPercentage();
        if (qualifiesForBulkDiscount) {
          discountPercentage += discount.getBundleDiscountPercentage();
        }

        double newBookPrice = bookPrice - (bookPrice * (discountPercentage / 100));
        totalDiscountPrice += newBookPrice;

        item.setDiscountPrice(newBookPrice);
      }
      purchaseRecord.addItem(item);
    }

    return totalDiscountPrice;
  }

  /**
   * Creates a PurchaseRecord by adding the customer info
   *
   * @param purchase the purchase entity
   * @return a populated PurchaseRecord
   */
  private PurchaseRecord toPurchaseRecord(Purchase purchase) {
    PurchaseRecord purchaseRecord = new PurchaseRecord();
    purchaseRecord.setPurchaseId(purchase.getPurchaseId());
    purchaseRecord.setCustomerId(purchase.getCustomerId());
    purchaseRecord.setTotalPrice(purchase.getTotalPrice());
    purchaseRecord.setRefunded(purchaseRecord.getRefunded());
    purchaseRecord.setItems(purchase.getItems());

    // add customer info to the record
    Customer customer = customerService.getCustomer(purchase.getCustomerId());
    if (customer != null) purchaseRecord.setCustomerInfo(customer);

    return purchaseRecord;
  }
}
