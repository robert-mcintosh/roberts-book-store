package com.mc.bookstore.service;

import com.mc.bookstore.config.exceptions.InvalidInputException;
import com.mc.bookstore.config.exceptions.NotFoundException;
import com.mc.bookstore.model.entities.*;
import com.mc.bookstore.model.responses.CustomerRecord;
import com.mc.bookstore.model.responses.PurchaseRecord;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.DiscountRepository;
import com.mc.bookstore.repository.PurchaseRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

  private final PurchaseRepository purchaseRepository;
  private final BookRepository bookRepository;
  private final DiscountRepository discountRepository;

  private final CustomerService customerService;

  public PurchaseService(
      PurchaseRepository purchaseRepository,
      BookRepository bookRepository,
      DiscountRepository discountRepository,
      CustomerService customerService) {
    this.purchaseRepository = purchaseRepository;
    this.bookRepository = bookRepository;
    this.discountRepository = discountRepository;
    this.customerService = customerService;
  }

  /**
   * Retrieves a purchase record by the given ID. This method fetches the purchase details from the
   * database, ensures the related items are initialized, and converts the purchase entity into a
   * purchase record.
   *
   * @param id the unique identifier of the purchase to retrieve
   * @return a {@link PurchaseRecord} containing the details of the purchase
   * @throws NotFoundException if the purchase does not exist or if the purchase items are not found
   */
  public PurchaseRecord getPurchase(Long id) {

    Purchase purchase = purchaseRepository.findById(id).orElse(null);

    if (purchase == null) throw new NotFoundException("Purchase does not exist!");
    if (purchase.getItems() == null) throw new NotFoundException("Purchase items not found!");

    // Explicitly initialize the 'items' collection
    Hibernate.initialize(purchase.getItems());

    return toPurchaseRecord(purchase);
  }

  /**
   * Processes a purchase for a given customer and a list of book IDs. This method retrieves the
   * customer and book details, checks for stock availability, calculates the total price with
   * applicable discounts, adjusts the book stock, updates customer loyalty points, and saves the
   * purchase record.
   *
   * @param customerId the unique identifier of the customer making the purchase
   * @param bookIds the list of unique identifiers for the books to be purchased
   * @return a {@code PurchaseRecord} object containing the details of the processed purchase
   * @throws NotFoundException if any of the specified books have insufficient stock
   */
  public PurchaseRecord makePurchase(Long customerId, List<Long> bookIds) {

    // get customer info
    Customer customer = customerService.getCustomer(customerId);

    // get books
    List<Book> books = bookRepository.findAllById(bookIds);

    // Check if any book has a quantity of zero
    boolean hasInsufficientStock = books.stream().anyMatch(book -> book.getQty() == 0);
    if (hasInsufficientStock) {
      throw new NotFoundException("One or more books have insufficient stock!");
    }

    // populate basics of book and save it to get a purchaseId
    Purchase purchase = new Purchase();
    purchase.setCustomerId(customer.getId());

    // save to generate an ID for the purchase
    purchaseRepository.saveAndFlush(purchase);

    // calculate the total price based on various factors
    double totalDiscountedPrice = calculateTotalPrice(purchase, books, customer);
    purchase.setTotalPrice(totalDiscountedPrice);

    // decrease stock of each book
    books.forEach(book -> bookRepository.decreaseStockByOne(book.getId()));

    // increment loyalty points
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() + books.size());
    customerService.updateLoyaltyPoints(customerId, customer.getLoyaltyPoints());

    purchaseRepository.saveAndFlush(purchase);

    return toPurchaseRecord(purchase);
  }

  /**
   * Handles the refund process for a specified purchase. This method verifies the existence of the
   * purchase, replenishes stock for the refunded items, deducts loyalty points from the customer,
   * and marks the purchase as refunded.
   *
   * @param purchaseId the unique identifier of the purchase to be refunded
   * @throws NotFoundException if the specified purchase does not exist
   */
  public void refund(Long purchaseId) {

    Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
    if (purchase == null) throw new NotFoundException("Purchase does not exist!");

    if (purchase.getRefunded())
      throw new InvalidInputException("Purchase has already been refunded!");

    List<PurchaseItem> purchaseItems = purchase.getItems();

    AtomicInteger countOfItemsRefunded = new AtomicInteger(purchaseItems.size());

    purchaseItems.forEach(
        item -> {

          // excluded any free books
          if (item.getDiscountPrice() == 0) countOfItemsRefunded.getAndDecrement();

          // need to replenish the book stock
          Book book = bookRepository.findById(item.getBookId()).orElse(null);
          if (book != null) {
            bookRepository.increaseStockByOne(book.getId());
          }
        });

    // deduct the loyalty points
    Customer customer = customerService.getCustomer(purchase.getCustomerId());
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() - countOfItemsRefunded.get());
    if (customer.getLoyaltyPoints() < 0) customer.setLoyaltyPoints(0);

    customerService.updateLoyaltyPoints(customer.getId(), customer.getLoyaltyPoints());

    // update purchase to REFUNDED
    purchase.setRefunded(true);
    purchaseRepository.saveAndFlush(purchase);
  }

  //  region private methods

  /**
   * Calculates the total discounted price for a purchase based on the provided list of books,
   * customer loyalty points, and applicable discounts. Discount types and loyalty rules are applied
   * to determine the final price.
   *
   * @param purchase the purchase entity representing the ongoing transaction
   * @param books the list of books included in the purchase
   * @param customer the customer making the purchase, used for applying loyalty points
   * @return the total discounted price for the purchase
   */
  private double calculateTotalPrice(Purchase purchase, List<Book> books, Customer customer) {
    /*
     * This is the brain of the operation
     */
    double totalDiscountPrice = 0.0;

    // get all the discount types and store them in a map to limit db access
    Map<String, Discount> discountMap = new HashMap<>();
    discountRepository
        .findAll()
        .forEach(discount -> discountMap.put(discount.getBookType(), discount));

    // ASSUMPTION: loyalty points are spent before an order
    // apply loyalty points before anything else
    while (customer.getLoyaltyPoints() >= 10) {
      for (Book book : books) {
        if (book.getPrice() > 0 && discountMap.get(book.getType()).getLoyaltyEligible()) {
          book.setPrice(0.0);
          customer.setLoyaltyPoints(customer.getLoyaltyPoints() - 10);
          break;
        }
      }
    }

    // ASSUMPTION: bulk discount is applied on cart size, not quantity of a particular type
    boolean qualifiesForBulkDiscount = books.size() >= 3;

    for (Book book : books) {
      PurchaseItem item = new PurchaseItem();
      item.setPurchaseId(purchase.getPurchaseId());
      item.setBookId(book.getId());
      item.setPrice(book.getPrice());

      Discount discount = discountMap.get(book.getType());
      if (discount != null) {
        double bookPrice = book.getPrice();

        double discountPercentage = discount.getDiscountPercentage();
        if (qualifiesForBulkDiscount) {
          discountPercentage += discount.getBundleDiscountPercentage();
        }
        double discountedPrice = bookPrice * (100 - discountPercentage) / 100;
        totalDiscountPrice += discountedPrice;

        item.setDiscountPrice(discountedPrice);
      }
      purchase.addItem(item);
    }

    return totalDiscountPrice;
  }

  //  endregion private methods

  // region entity conversion

  /**
   * Converts a {@link Purchase} and a {@link Customer} into a {@link PurchaseRecord}. This method
   * populates the {@link PurchaseRecord} with details from the given purchase and customer.
   *
   * @param purchase the purchase entity containing purchase details
   * @param customer the customer entity containing customer details
   * @return a {@link PurchaseRecord} containing combined purchase and customer details
   */
  private PurchaseRecord toPurchaseRecord(Purchase purchase, Customer customer) {
    PurchaseRecord purchaseRecord = new PurchaseRecord().populateFromPurchase(purchase);
    purchaseRecord.setCustomerInfo(new CustomerRecord().populateFromCustomer(customer));
    return purchaseRecord;
  }

  /**
   * Converts a {@link Purchase} into a {@link PurchaseRecord} by retrieving the associated {@link
   * Customer} details and populating the {@link PurchaseRecord} with combined purchase and customer
   * information.
   *
   * @param purchase the purchase entity containing purchase details
   * @return a {@link PurchaseRecord} containing combined purchase and customer information
   * @throws RuntimeException if the customer associated with the purchase cannot be found
   */
  private PurchaseRecord toPurchaseRecord(Purchase purchase) {
    Customer customer = customerService.getCustomer(purchase.getCustomerId());
    return toPurchaseRecord(purchase, customer);
  }
}
