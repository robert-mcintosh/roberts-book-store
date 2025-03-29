package com.mc.bookstore.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookRq {
  private Long id;
  private String title;
  private String author;
  private int qty;
  private double price;
  private String type;
}
