package com.mc.bookstore.controllers;

import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Customers")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  @Autowired private CustomerService customerService;

  @GetMapping("/{id}")
  @Operation(summary = "Get a customer by id")
  public Customer getCustomer(@PathVariable Long id) {
    return customerService.getCustomer(id);
  }
}
