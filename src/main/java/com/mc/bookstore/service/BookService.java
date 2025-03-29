package com.mc.bookstore.service;

import com.mc.bookstore.config.exceptions.InvalidInputException;
import com.mc.bookstore.config.exceptions.NotFoundException;
import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.model.requests.BookRq;
import com.mc.bookstore.model.responses.BookRecord;
import com.mc.bookstore.repository.BookRepository;
import com.mc.bookstore.repository.DiscountRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  @Autowired private BookRepository bookRepository;
  @Autowired private DiscountRepository discountRepository;

  public List<BookRecord> getAllBooks() {
    List<BookRecord> bookRecords = new ArrayList<>();

    for (Book book : bookRepository.findAll()) {
      bookRecords.add(new BookRecord().populateFromBook(book));
    }

    return bookRecords;
  }

  public BookRecord addBook(BookRq bookRq) {

    discountRepository
        .findByBookType(bookRq.getType())
        .orElseThrow(() -> new NotFoundException("Book type not found"));

    if (bookRq.getQty() < 0) throw new InvalidInputException("Quantity must be greater than 0!");
    if (bookRq.getTitle().isBlank()) throw new InvalidInputException("Book title cannot be blank!");
    if (bookRq.getAuthor().isBlank())
      throw new InvalidInputException("Book author cannot be blank!");
    if (bookRq.getPrice() <= 0)
      throw new InvalidInputException("Book price must be greater than 0");

    Book book = new Book();
    book.setAuthor(bookRq.getAuthor());
    book.setTitle(bookRq.getTitle());
    book.setPrice(bookRq.getPrice());
    book.setType(bookRq.getType());
    book.setQty(bookRq.getQty());

    bookRepository.save(book);

    return new BookRecord().populateFromBook(book);
  }

  public void deleteBook(Long id) {
    var exists = bookRepository.findById(id).orElse(null);
    if (exists == null) throw new NotFoundException("Book does not exist!");
    bookRepository.deleteById(id);
  }

  public BookRecord updateBook(BookRq bookRq) {

    if (bookRq.getId() == null) throw new InvalidInputException("Book ID cannot be null!");

    Book book = bookRepository.findById(bookRq.getId()).orElse(null);
    if (book == null) throw new NotFoundException("Book does not exist!");

    if (bookRq.getQty() < 0) throw new InvalidInputException("Quantity must be greater than 0!");
    if (bookRq.getTitle().isBlank()) throw new InvalidInputException("Book title cannot be blank!");
    if (bookRq.getAuthor().isBlank()) {
      throw new InvalidInputException("Book author cannot be blank!");
    }
    if (bookRq.getPrice() <= 0) {
      throw new InvalidInputException("Book price must be greater than 0");
    }

    book.setAuthor(bookRq.getAuthor());
    book.setTitle(bookRq.getTitle());
    book.setPrice(bookRq.getPrice());
    book.setType(bookRq.getType());
    book.setQty(bookRq.getQty());

    bookRepository.save(book);

    return new BookRecord().populateFromBook(book);
  }
}
