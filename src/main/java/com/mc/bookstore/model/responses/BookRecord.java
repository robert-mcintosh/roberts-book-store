package com.mc.bookstore.model.responses;

import com.mc.bookstore.model.entities.Book;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class BookRecord extends Book {

  public BookRecord populateFromBook(Book book) {
    this.setId(book.getId());
    this.setTitle(book.getTitle());
    this.setAuthor(book.getAuthor());
    this.setQty(book.getQty());
    this.setPrice(book.getPrice());
    this.setType(book.getType());
    return this;
  }
}
