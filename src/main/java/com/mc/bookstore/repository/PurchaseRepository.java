package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.Purchase;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  @EntityGraph(attributePaths = "items")
  Optional<Purchase> findById(Long id);
}
