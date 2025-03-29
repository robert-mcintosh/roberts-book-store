package com.mc.bookstore.controllers;

import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.model.requests.BookRq;
import com.mc.bookstore.model.responses.BookRecord;
import com.mc.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books")
@RestController
@RequestMapping("/api/books")
public class BookController {
  @Autowired private BookService bookService;

  @GetMapping
  @Operation(summary = "Get all books")
  public List<BookRecord> getAllBooks() {
    return bookService.getAllBooks();
  }

  @PostMapping
  @Operation(summary = "Add a book")
  public BookRecord addBook(@RequestBody BookRq bookRq) {
    return bookService.addBook(bookRq);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a book")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }

  @PutMapping
  @Operation(summary = "Update a book")
  public BookRecord updateBook(@RequestBody BookRq bookRq) {
    return bookService.updateBook(bookRq);
  }
}
