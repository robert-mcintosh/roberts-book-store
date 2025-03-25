package com.mc.bookstore.service;

import com.mc.bookstore.entities.Book;
import com.mc.bookstore.entities.Customer;
import com.mc.bookstore.entities.Purchase;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
  @Autowired private PurchaseRepository purchaseRepository;
  @Autowired private BookRepository bookRepository;
  @Autowired private CustomerService customerService;

  public Purchase makePurchase(Long customerId, List<Long> bookIds) {
    Customer customer = customerService.getCustomer(customerId);
    List<Book> books = (List<Book>) bookRepository.findAllById(bookIds);
    double totalPrice = calculateTotalPrice(books);
    Purchase purchase = new Purchase();
    purchase.setCustomer(customer);
    purchase.setBooks(books);
    purchase.setTotalPrice(totalPrice);
    customer.setLoyaltyPoints(customer.getLoyaltyPoints() + books.size());
    customerService.updateLoyaltyPoints(customerId, customer.getLoyaltyPoints());
    return purchaseRepository.save(purchase);
  }

  private double calculateTotalPrice(List<Book> books) {
    // Implement pricing logic based on book type and bundle discounts
    // todo
    return 0;
  }
}
