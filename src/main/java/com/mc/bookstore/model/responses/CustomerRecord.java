package com.mc.bookstore.model.responses;

import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.model.entities.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CustomerRecord extends Customer {

  public CustomerRecord populateFromCustomer(Customer customer) {
    this.setId(customer.getId());
    this.setName(customer.getName());
    this.setLoyaltyPoints(customer.getLoyaltyPoints());
    return this;
  }
}
