package com.mc.bookstore.controllers;

import com.mc.bookstore.model.requests.PurchaseRq;
import com.mc.bookstore.model.responses.PurchaseRecord;
import com.mc.bookstore.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Purchases")
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
  @Autowired private PurchaseService purchaseService;

  @GetMapping
  @Operation(summary = "Get a purchase")
  public PurchaseRecord getPurchase(@RequestParam Long purchaseId) {
    return purchaseService.getPurchase(purchaseId);
  }

  @PostMapping
  @Operation(summary = "Makes a purchase")
  public PurchaseRecord makePurchase(@RequestBody PurchaseRq purchaseRq) {
    return purchaseService.makePurchase(purchaseRq.getCustomerId(), purchaseRq.getBookIds());
  }

  @PostMapping("/refund")
  @Operation(summary = "Refund a purchase")
  public void refundPurchase(@RequestParam Long purchaseId) {
    purchaseService.refund(purchaseId);
  }
}
