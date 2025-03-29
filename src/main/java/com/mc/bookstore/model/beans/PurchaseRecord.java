package com.mc.bookstore.model.beans;

import com.mc.bookstore.model.entities.Customer;
import com.mc.bookstore.model.entities.Purchase;
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

  private Customer customerInfo;
}
