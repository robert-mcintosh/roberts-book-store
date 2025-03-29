package com.mc.bookstore.model.beans;

import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.model.entities.Purchase;
import com.mc.bookstore.model.entities.PurchaseItem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PurchaseRecord extends Purchase {

  private List<PurchaseItem> items = new ArrayList<>();

  private Customer customerInfo;

  public void addItem(PurchaseItem item) {
    items.add(item);
  }

  public void removeItem(PurchaseItem item) {
    items.remove(item);
  }

}
