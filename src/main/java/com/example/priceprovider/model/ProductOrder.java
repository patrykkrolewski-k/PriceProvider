package com.example.priceprovider.model;

import java.util.UUID;

public record ProductOrder(
        UUID productUuid,
        int amount,
        DiscountPolicy discountPolicy
) {

}
