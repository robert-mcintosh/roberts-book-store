package com.mc.bookstore.service;

import com.mc.bookstore.config.exceptions.InvalidInputException;
import com.mc.bookstore.model.entities.Book;
import com.mc.bookstore.model.entities.Discount;
import com.mc.bookstore.config.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import com.mc.bookstore.model.requests.DiscountRq;
import com.mc.bookstore.model.responses.BookRecord;
import com.mc.bookstore.model.responses.DiscountRecord;
import com.mc.bookstore.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
  @Autowired private DiscountRepository discountRepository;

  /**
   * Retrieves a list of all discounts stored in the repository. Each discount is converted into a
   * DiscountRecord instance before being added to the result.
   *
   * @return a list of DiscountRecord objects representing all discounts in the repository
   */
  public List<DiscountRecord> getAllDiscounts() {

    List<DiscountRecord> discountRecords = new ArrayList<>();

    for (Discount discount : discountRepository.findAll())
      discountRecords.add(new DiscountRecord().populateFromDiscount(discount));

    return discountRecords;
  }

  /**
   * Updates the details of an existing discount based on the provided request data.
   *
   * @param discountRq the request object containing the details to update the discount, including
   *     book type, description, discount percentage, bundle discount percentage, and loyalty
   *     eligibility
   * @return a DiscountRecord object populated with the updated discount details
   * @throws InvalidInputException if the book type is null or the description is blank
   * @throws NotFoundException if no discount exists for the specified book type
   */
  public DiscountRecord updateDiscount(DiscountRq discountRq) {
    if (discountRq.getBookType() == null)
      throw new InvalidInputException("Book type cannot be null!");

    Discount discount = discountRepository.findByBookType(discountRq.getBookType()).orElse(null);
    if (discount == null) throw new NotFoundException("Discount does not exist!");

    if (discountRq.getDescription().isBlank())
      throw new InvalidInputException("Description cannot be blank!");

    discount.setDescription(discountRq.getDescription());
    discount.setDiscountPercentage(discountRq.getDiscountPercentage());
    discount.setBundleDiscountPercentage(discountRq.getBundleDiscountPercentage());
    discount.setLoyaltyEligible(discountRq.isLoyaltyEligible());

    discountRepository
        .findByBookType(discount.getBookType())
        .orElseThrow(() -> new NotFoundException("Discount does not exist!"));
    discountRepository.save(discount);

    return new DiscountRecord().populateFromDiscount(discount);
  }
}
