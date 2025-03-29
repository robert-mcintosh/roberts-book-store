package com.mc.bookstore.service;

import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.model.responses.CustomerRecord;
import com.mc.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  @Autowired private CustomerRepository customerRepository;

  /**
   * Retrieves the customer record associated with the specified unique identifier. If no customer
   * is found with the given ID, an exception is thrown.
   *
   * @param id the unique identifier of the customer whose record is to be retrieved
   * @return a CustomerRecord object containing the details of the requested customer
   * @throws RuntimeException if no customer is found with the specified ID
   */
  public CustomerRecord getCustomerRecord(Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    return new CustomerRecord().populateFromCustomer(customer);
  }

  /**
   * Retrieves a customer from the repository based on the provided unique identifier. If no
   * customer with the specified ID exists, an exception is thrown.
   *
   * @param id the unique identifier of the customer to be retrieved
   * @return the Customer object corresponding to the provided ID
   * @throws RuntimeException if no customer with the specified ID is found
   */
  public Customer getCustomer(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

  /**
   * Updates the loyalty points of a specific customer based on the provided customer ID. Retrieves
   * the existing customer record, modifies the loyalty points, and saves the updated record in the
   * repository. If no customer is found with the provided ID, an exception is thrown.
   *
   * @param customerId the unique identifier of the customer whose loyalty points are being updated
   * @param points the new loyalty points to be set for the customer
   * @return the updated Customer object with the modified loyalty points
   * @throws RuntimeException if no customer with the specified ID is found
   */
  public Customer updateLoyaltyPoints(Long customerId, int points) {
    Customer customer = getCustomer(customerId);
    customer.setLoyaltyPoints(points);
    return customerRepository.save(customer);
  }
}
