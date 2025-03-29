package com.mc.bookstore.model.responses;

import com.mc.bookstore.model.entities.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRecord  {

  private Long purchaseId = null;
  private Long customerId;
  private double totalPrice;
  private boolean refunded; // Maps 'Y' or 'N' to boolean

  private List<PurchaseItemRecord> purchaseItems = new ArrayList<>();
  private CustomerRecord customerInfo;

  public PurchaseRecord populateFromPurchase(Purchase purchase) {
    this.setPurchaseId(purchase.getPurchaseId());
    this.setCustomerId(purchase.getCustomerId());
    this.setTotalPrice(purchase.getTotalPrice());
    this.setRefunded(purchase.getRefunded());
    purchase
        .getItems()
        .forEach(
            item ->
                this.getPurchaseItems()
                    .add(new PurchaseItemRecord().populateFromPurchaseItem(item)));
    return this;
  }
}
