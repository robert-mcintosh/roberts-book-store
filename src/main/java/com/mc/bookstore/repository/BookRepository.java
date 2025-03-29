package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
  Book findByTitle(String title);
}
