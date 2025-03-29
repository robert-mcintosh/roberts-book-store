package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.Purchase;

import java.util.List;
import java.util.Optional;

import com.mc.bookstore.model.entities.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

 List<PurchaseItem> findByPurchaseId(Long purchase_id);

  
}
