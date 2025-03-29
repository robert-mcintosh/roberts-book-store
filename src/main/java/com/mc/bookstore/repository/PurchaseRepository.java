package com.mc.bookstore.repository;

import com.mc.bookstore.model.beans.PurchaseRecord;
import com.mc.bookstore.model.entities.Purchase;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

  Optional<Purchase> findById(Long id);

}
