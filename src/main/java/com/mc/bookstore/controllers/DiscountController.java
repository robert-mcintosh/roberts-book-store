package com.mc.bookstore.controllers;

import com.mc.bookstore.model.entities.Discount;

import java.util.List;

import com.mc.bookstore.model.requests.DiscountRq;
import com.mc.bookstore.model.responses.DiscountRecord;
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
  public List<DiscountRecord> getAllDiscounts() {
    return discountService.getAllDiscounts();
  }

  @PutMapping
  @Operation(summary = "Update a discount")
  public DiscountRecord updateDiscount(@RequestBody DiscountRq discountRq) {
    return discountService.updateDiscount(discountRq);
  }
}
