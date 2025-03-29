package com.mc.bookstore.service;

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

  /**
   * Creates a new purchase record for the specified customer and list of book IDs. This method
   * validates stock availability, calculates the total price with discounts, updates book stock,
   * increments customer loyalty points, and saves the purchase record.
   *
   * @param customerId the ID of the customer making the purchase
   * @param bookIds the list of book IDs to be purchased by the customer
   * @return the {@link PurchaseRecord} containing details of the completed purchase
   * @throws NotFoundException if any of the books are out of stock
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
   * Processes a refund for the given purchase ID. This method retrieves the purchase record by the
   * specified ID and performs refund operations if applicable.
   *
   * @param purchaseId the ID of the purchase to be refunded
   * @return the {@link PurchaseRecord} containing details of the refunded purchase
   * @throws NotFoundException if the purchase with the given ID does not exist
   */
  public void refund(Long purchaseId) {
    Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
    if (purchase == null) throw new NotFoundException("Purchase does not exist!");

    List<PurchaseItem> purchaseItems = purchase.getItems();

    AtomicInteger countOfItemsRefunded = new AtomicInteger(purchaseItems.size());

    purchaseItems.forEach(item -> {

      // excluded any free books
      if(item.getDiscountPrice() == 0)
        countOfItemsRefunded.getAndDecrement();

      // need to replenish the book stock
      Book book = bookRepository.findById(item.getBookId()).orElse(null);
      if(book != null){
        bookRepository.increaseStockByOne(book.getId());
      }
    });

    // deduct the loyalty points
    Customer customer = customerService.getCustomer(purchase.getCustomerId());
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() - countOfItemsRefunded.get());
    if(customer.getLoyaltyPoints() < 0) customer.setLoyaltyPoints(0);

    customerService.updateLoyaltyPoints(customer.getId(), customer.getLoyaltyPoints());

    // update purchase to refunded
    purchase.setRefunded(true);
    purchaseRepository.saveAndFlush(purchase);
  }

  //  region private methods

  /**
   * Calculates the total discounted price of a purchase, applying applicable discounts, loyalty
   * points, and bulk discounts to the books in the purchase.
   *
   * @param purchase the purchase entity where the items are added
   * @param books the list of books included in the purchase
   * @param customer the customer making the purchase, whose loyalty points are updated
   * @return the total price of the purchase after applying discounts and loyalty points
   */
  private double calculateTotalPrice(Purchase purchase, List<Book> books, Customer customer) {
    double totalDiscountPrice = 0.0;

    // get all the discount types and store them it a map to limit db access
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

        double newBookPrice = bookPrice - (bookPrice * (discountPercentage / 100));
        totalDiscountPrice += newBookPrice;

        item.setDiscountPrice(newBookPrice);
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
   * Converts a {@link Purchase} entity into a {@link PurchaseRecord} by adding customer details.
   *
   * @param purchase the purchase entity to convert
   * @return a {@link PurchaseRecord} containing purchase and customer details
   */
  private PurchaseRecord toPurchaseRecord(Purchase purchase) {
    Customer customer = customerService.getCustomer(purchase.getCustomerId());
    return toPurchaseRecord(purchase, customer);
  }
}
