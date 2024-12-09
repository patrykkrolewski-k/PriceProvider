package com.example.priceprovider.model;

import java.math.BigDecimal;

public record  DiscountBid(
        Product product,
        DiscountPolicy discountPolicy,
        int amount,
        BigDecimal offeredPrice
) {
}
