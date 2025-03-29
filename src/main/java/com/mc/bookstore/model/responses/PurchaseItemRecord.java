package com.mc.bookstore.model.responses;

import com.mc.bookstore.model.entities.PurchaseItem;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemRecord {

  private Long id;
  private Long purchaseId;
  private Long bookId;
  private double price;
  private double discountPrice;

  public PurchaseItemRecord populateFromPurchaseItem(PurchaseItem purchaseItem) {
    this.setId(purchaseItem.getId());
    this.setPurchaseId(purchaseItem.getPurchaseId());
    this.setBookId(purchaseItem.getBookId());
    this.setPrice(purchaseItem.getPrice());
    this.setDiscountPrice(purchaseItem.getDiscountPrice());
    return this;
  }
}
