package com.mc.bookstore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book") // Maps this class to "book" table
@Getter
@Setter
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;
  
  private String author;
  
  private double price;
  
  @Column(nullable = false)
  private String type; // NEW Release, REQular, OLD Edition

  public Book(String title, String author, double price, String type) {
    this.title = title;
    this.author = author;
    this.price = price;
    this.type = type;
  }
}
