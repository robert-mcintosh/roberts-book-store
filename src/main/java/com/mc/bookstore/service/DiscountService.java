package com.mc.bookstore.service;

import com.mc.bookstore.model.entities.Discount;
import com.mc.bookstore.config.exceptions.NotFoundException;

import java.util.List;

import com.mc.bookstore.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
  @Autowired private DiscountRepository discountRepository;

  public List<Discount> getAllDiscounts() {
    return (List<Discount>) discountRepository.findAll();
  }

  public Discount updateDiscount(Discount discount) {
    discountRepository
        .findByBookType(discount.getBookType())
        .orElseThrow(() -> new NotFoundException("Discount does not exist!"));
    return discountRepository.save(discount);
  }
}
