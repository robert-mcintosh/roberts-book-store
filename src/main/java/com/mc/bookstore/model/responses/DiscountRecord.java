package com.mc.bookstore.model.responses;

import com.mc.bookstore.model.entities.Discount;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class DiscountRecord extends Discount {

  public DiscountRecord populateFromDiscount(Discount discount) {
    this.setBookType(discount.getBookType());
    this.setDescription(discount.getDescription());
    this.setDiscountPercentage(discount.getDiscountPercentage());
    this.setBundleDiscountPercentage(discount.getBundleDiscountPercentage());
    this.setLoyaltyEligible(discount.getLoyaltyEligible());
    this.setCreateDate(discount.getCreateDate());
    this.setUpdateDate(discount.getUpdateDate());
    return this;
  }
}
