package com.mc.bookstore.model.responses;

import com.mc.bookstore.model.entities.Purchase;
import com.mc.bookstore.model.entities.PurchaseItem;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemRecord  {

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
