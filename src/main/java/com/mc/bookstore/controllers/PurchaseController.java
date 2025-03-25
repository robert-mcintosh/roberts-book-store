package com.mc.bookstore.controllers;

import com.mc.bookstore.entities.Purchase;
import com.mc.bookstore.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
  @Autowired private PurchaseService purchaseService;

  @PostMapping
  public Purchase makePurchase(@RequestParam Long customerId, @RequestParam List<Long> bookIds) {
    return purchaseService.makePurchase(customerId, bookIds);
  }
}
