package com.mc.bookstore.controllers;

import com.mc.bookstore.model.entities.Purchase;
import com.mc.bookstore.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Purchases")
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
  @Autowired private PurchaseService purchaseService;

  @GetMapping
  @Operation(summary = "Get a purchase")
  public Purchase getPurchase(@RequestParam Long id) {
    return purchaseService.getPurchase(id);
  }

  @PostMapping
  @Operation(summary = "Makes a purchase")
  public Purchase makePurchase(@RequestParam Long customerId, @RequestParam List<Long> bookIds) {
    return purchaseService.makePurchase(customerId, bookIds);
  }
  @PostMapping("/refund")
  @Operation(summary = "Refund a purchase")
  public Purchase makePurchase(@RequestParam Long purchaseId) {
    return purchaseService.refund(purchaseId);
  }
}
