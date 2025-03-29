package com.mc.bookstore.controllers;

import com.mc.bookstore.model.entities.Book;
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
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @PostMapping
  @Operation(summary = "Add a book")
  public Book addBook(@RequestBody Book book) {
    return bookService.addBook(book);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a book")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }

  @PutMapping
  @Operation(summary = "Update a book")
  public Book updateBook(@RequestBody Book book) {
    return bookService.updateBook(book);
  }
}
