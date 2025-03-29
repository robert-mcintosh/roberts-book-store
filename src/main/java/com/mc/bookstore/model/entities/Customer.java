package com.mc.bookstore.model.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int loyaltyPoints;

  public Customer(String name, int loyaltyPoints) {
    this.name = name;
    this.loyaltyPoints = loyaltyPoints;
  }
}
