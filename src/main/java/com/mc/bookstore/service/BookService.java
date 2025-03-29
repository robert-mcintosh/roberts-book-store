package com.mc.bookstore.service;

import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.config.exceptions.NotFoundException;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.DiscountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  @Autowired private BookRepository bookRepository;
  @Autowired private DiscountRepository discountRepository;

  public List<Book> getAllBooks() {
    return (List<Book>) bookRepository.findAll();
  }

  public Book addBook(Book book) {
    discountRepository
        .findByBookType(book.getType())
        .orElseThrow(() -> new NotFoundException("Book type not found"));
    return bookRepository.save(book);
  }

  public void deleteBook(Long id) {
    var exists = bookRepository.findById(id).orElse(null);
    if (exists == null) throw new NotFoundException("Book does not exist!");
    bookRepository.deleteById(id);
  }

  public Book updateBook(Book book) {
    var exists = bookRepository.findById(book.getId()).orElse(null);
    if (exists == null) throw new NotFoundException("Book does not exist!");
    return bookRepository.save(book);
  }
}
