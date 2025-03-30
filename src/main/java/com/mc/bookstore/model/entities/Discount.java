package com.mc.bookstore.model.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Column(name = "create_date")
  @CreationTimestamp
  private LocalDateTime createDate;

  @Column(name = "update_date")
  @UpdateTimestamp
  private LocalDateTime updateDate;

  public boolean getLoyaltyEligible() {
    return loyaltyEligible == 'Y';
  }

  public void setLoyaltyEligible(boolean loyaltyEligible) {
    this.loyaltyEligible = loyaltyEligible ? 'Y' : 'N';
  }
}
