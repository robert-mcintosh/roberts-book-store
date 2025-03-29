package com.mc.bookstore.model.requests;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CustomerRq {
  private Long id;
  private String name;
  private int loyaltyPoints;
}
