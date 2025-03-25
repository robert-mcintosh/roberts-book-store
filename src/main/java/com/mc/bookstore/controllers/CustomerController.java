package com.mc.bookstore.controllers;

import com.mc.bookstore.entities.Customer;
import com.mc.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  @Autowired private CustomerService customerService;

  @GetMapping("/{id}")
  public Customer getCustomer(@PathVariable Long id) {
    return customerService.getCustomer(id);
  }
}
