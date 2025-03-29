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

  /**
   * Retrieves a list of all books stored in the repository. Each book is converted into a
   * BookRecord instance before being added to the result.
   *
   * @return a list of BookRecord objects representing all books in the repository
   */
  public List<BookRecord> getAllBooks() {
    List<BookRecord> bookRecords = new ArrayList<>();

    for (Book book : bookRepository.findAll()) {
      bookRecords.add(new BookRecord().populateFromBook(book));
    }

    return bookRecords;
  }

  /**
   * Adds a new book to the repository. Validates the provided book data, including ensuring that
   * the book quantity, title, author, and price meet the application's constraints. Throws
   * exceptions for invalid input or if the book type does not exist in the repository.
   *
   * @param bookRq the data transfer object containing details of the book to be added
   * @return a BookRecord object representing the added book
   * @throws NotFoundException if the specified book type does not exist
   * @throws InvalidInputException if the book quantity is less than 0, title or author is blank, or
   *     the price is less than or equal to 0
   */
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

  /**
   * Deletes a book from the repository based on the specified ID. If the book with the given ID
   * does not exist, an exception is thrown.
   *
   * @param id the unique identifier of the book to be deleted
   * @throws NotFoundException if no book with the specified ID is found in the repository
   */
  public void deleteBook(Long id) {
    var exists = bookRepository.findById(id).orElse(null);
    if (exists == null) throw new NotFoundException("Book does not exist!");
    bookRepository.deleteById(id);
  }

  /**
   * Updates an existing book in the repository with the provided details. Validates the input data
   * including ensuring that the book quantity, title, author, and price meet specific constraints.
   * If the book with the given ID does not exist, or if the input data is invalid, appropriate
   * exceptions are thrown.
   *
   * @param bookRq the data transfer object containing updated details for the book
   * @return a BookRecord object representing the updated book
   * @throws InvalidInputException if the book ID is null, the quantity is less than 0, the title or
   *     author is blank, or the price is less than or equal to 0
   * @throws NotFoundException if the book with the specified ID does not exist in the repository
   */
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
