package com.mc.bookstore.entities;

import java.util.List;
import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Long customerId;
  @ManyToMany private List<Book> books; //fixme possible store as json?
  private double totalPrice;
}
