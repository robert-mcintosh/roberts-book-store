package com.mc.bookstore.model.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@Getter
public class DiscountRq {
    private String bookType; // NEW Release, REGular, OLD Edition
    private String description;
    private double discountPercentage;
    private double bundleDiscountPercentage;
    private boolean loyaltyEligible;

}
