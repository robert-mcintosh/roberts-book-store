package com.mc.bookstore.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
  @Id private String bookType; // NEW Release, REGular, OLD Edition
  private String description;
  private double discountPercentage;
  private double bundleDiscountPercentage;
  private char loyaltyEligible;

  public boolean getLoyaltyEligible() {
    return loyaltyEligible == 'Y';
  }

  public void setLoyaltyEligible(boolean loyaltyEligible) {
    this.loyaltyEligible = loyaltyEligible ? 'Y' : 'N';
  }
}
