package com.mc.bookstore.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discount")
@Getter
@Setter
@NoArgsConstructor
public class Discount {
  @Id private String bookType; // NEW Release, REGular, OLD Edition
  private String description;
  private double discountPercentage;
  private double bundleDiscountPercentage;
}
