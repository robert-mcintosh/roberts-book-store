package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, String> {
  Optional<Discount> findByBookType(String bookType);
}
