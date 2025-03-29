package com.mc.bookstore.model.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book") // Maps this class to "book" table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String author;
  private int qty;
  private double price;

  @Column(nullable = false)
  private String type; // NEW, REG, OLD

  public Book(String title, String author, double price, String type, int qty) {
    this.title = title;
    this.author = author;
    this.price = price;
    this.type = type;
    this.qty = qty;
  }

  @Override
  public String toString() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String json = objectMapper.writeValueAsString(this);
      return json;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
