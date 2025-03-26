package com.mc.bookstore.service;

import com.mc.bookstore.entities.Book;
import com.mc.bookstore.entities.Customer;
import com.mc.bookstore.entities.Purchase;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.DiscountRepository;
import com.mc.bookstore.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
  @Autowired private PurchaseRepository purchaseRepository;
  @Autowired private BookRepository bookRepository;
  @Autowired private DiscountRepository discountRepository;
  @Autowired private CustomerService customerService;

  public Purchase makePurchase(Long customerId, List<Long> bookIds) {
    Customer customer = customerService.getCustomer(customerId);
    List<Book> books = (List<Book>) bookRepository.findAllById(bookIds);
    double totalPrice = calculateTotalPrice(books);
    Purchase purchase = new Purchase();
    purchase.setCustomerId(customer.getId());
    purchase.setBooks(books);
    purchase.setTotalPrice(totalPrice);
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() + books.size());
    customerService.updateLoyaltyPoints(customerId, customer.getLoyaltyPoints());
    return purchaseRepository.save(purchase);
  }

  public double calculateTotalPrice(List<Book> books) {
    // Implement pricing logic based on book type and bundle discounts
    // todo
    return 0;
  }

  /*
  private double calculateTotalPrice(List<Book> books) {
        double totalPrice = 0.0;
        Map<String, Long> bookTypeCounts = books.stream()
                .collect(Collectors.groupingBy(Book::getType, Collectors.counting()));

        for (Book book : books) {
            Optional<Discount> discountOpt = discountRepository.findByBookType(book.getType());
            if (discountOpt.isPresent()) {
                Discount discount = discountOpt.get();
                double discountPercentage = discount.getDiscountPercentage();
                double bundleDiscountPercentage = discount.getBundleDiscountPercentage();
                double bookPrice = book.getPrice();

                if (bookTypeCounts.get(book.getType()) >= 3) {
                    bookPrice -= bookPrice * (bundleDiscountPercentage / 100);
                }
                bookPrice -= bookPrice * (discountPercentage / 100);
                totalPrice += bookPrice;
            }
        }
        return totalPrice;
    }

   */
}
