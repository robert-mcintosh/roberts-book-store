package com.mc.bookstore.model.requests;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class PurchaseRq implements Serializable {
  private Long customerId;
  private List<Long> bookIds;
}
