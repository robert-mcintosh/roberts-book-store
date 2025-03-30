package com.mc.bookstore.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Column(name = "create_date")
  @CreationTimestamp
  private LocalDateTime createDate;

  @Column(name = "update_date")
  @UpdateTimestamp
  private LocalDateTime updateDate;

  /*
   * custom getters and setters
   */

  public boolean getRefunded() {
    return refunded == 'Y';
  }

  public void setRefunded(boolean refunded) {
    this.refunded = refunded ? 'Y' : 'N';
  }

  public void addItem(PurchaseItem item) {
    items.add(item);
  }

  public void removeItem(PurchaseItem item) {
    items.remove(item);
  }
}
