package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE Book b SET b.qty = b.qty - 1 WHERE b.id = :id AND b.qty > 0")
  void decreaseStockByOne(@Param("id") Long id);

  @Modifying
  @Transactional
  @Query("UPDATE Book b SET b.qty = b.qty + 1 WHERE b.id = :id AND b.qty > 0")
  void increaseStockByOne(@Param("id") Long id);

}
