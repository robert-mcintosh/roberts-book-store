package com.mc.bookstore.repository;

import com.mc.bookstore.entities.Book;
import com.mc.bookstore.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
  Optional<Discount> findByBookType(String bookType);
}
