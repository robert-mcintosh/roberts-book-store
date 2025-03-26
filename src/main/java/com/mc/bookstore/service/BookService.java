package com.mc.bookstore.service;

import com.mc.bookstore.entities.Book;
import com.mc.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
  @Autowired private BookRepository bookRepository;

  public List<Book> getAllBooks() {
    return (List<Book>) bookRepository.findAll();
  }

  public Book addBook(Book book) {
    return bookRepository.save(book);
  }

  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }
}
