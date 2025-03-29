package com.mc.bookstore.repository;

import com.mc.bookstore.model.entities.PurchaseItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

  List<PurchaseItem> findByPurchaseId(Long purchase_id);
}
