package com.mc.bookstore.model.requests;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class DiscountRq {
  private String bookType; // NEW Release, REGular, OLD Edition
  private String description;
  private double discountPercentage;
  private double bundleDiscountPercentage;
  private boolean loyaltyEligible;
}
