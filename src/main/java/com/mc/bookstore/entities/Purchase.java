package com.mc.bookstore.entities;

import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Purchase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Customer customer;
  @ManyToMany private List<Book> books;
  private double totalPrice;
}
