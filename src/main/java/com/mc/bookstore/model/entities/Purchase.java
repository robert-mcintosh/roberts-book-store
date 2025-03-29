package com.mc.bookstore.model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Purchase")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "purchase_id")
  private Long purchaseId = null;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "total_price")
  private double totalPrice;

  @Column(name = "refunded")
  private char refunded; // Maps 'Y' or 'N' to boolean

  @OneToMany(
      mappedBy = "purchaseId",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      targetEntity = PurchaseItem.class)
  private List<PurchaseItem> items = new ArrayList<>();

  public boolean getRefunded() {
    return refunded == 'Y';
  }

  public void setRefunded(boolean refunded) {
    this.refunded = refunded ? 'Y' : 'N';
  }

  public void addItem(PurchaseItem item) {
    items.add(item);
    item.setPurchase(this);
  }

  public void removeItem(PurchaseItem item) {
    items.remove(item);
    item.setPurchase(null);
  }
}
