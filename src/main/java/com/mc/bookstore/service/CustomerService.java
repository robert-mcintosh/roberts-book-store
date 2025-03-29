package com.mc.bookstore.service;

import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  @Autowired private CustomerRepository customerRepository;

  public Customer getCustomer(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

  public Customer updateLoyaltyPoints(Long customerId, int points) {
    Customer customer = getCustomer(customerId);
    customer.setLoyaltyPoints(points);
    return customerRepository.save(customer);
  }
}
