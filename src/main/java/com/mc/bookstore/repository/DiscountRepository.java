package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.Discount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, String> {
  Optional<Discount> findByBookType(String bookType);
}
