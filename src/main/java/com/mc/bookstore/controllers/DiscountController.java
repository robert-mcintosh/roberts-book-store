package com.mc.bookstore.controllers;

import com.mc.bookstore.model.entities.Discount;

import java.util.List;

import com.mc.bookstore.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Book Discounts")
@RestController
@RequestMapping("/api/book/discount")
public class DiscountController {
  @Autowired private DiscountService discountService;

  @GetMapping
  @Operation(summary = "Get all book discounts")
  public List<Discount> getAllDiscounts() {
    return discountService.getAllDiscounts();
  }

  @PutMapping
  @Operation(summary = "Update a discount")
  public Discount updateDiscount(Discount discount) {
    return discountService.updateDiscount(discount);
  }
}
