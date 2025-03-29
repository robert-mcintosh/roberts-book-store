package com.mc.bookstore.model.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Purchase_Item")
@Data
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "purchase_id")
  private Long purchaseId;

  @Column(name = "book_id")
  private Long bookId;

  @Column(name = "price")
  private double price;

  @Column(name = "discount_price")
  private double discountPrice;


}
