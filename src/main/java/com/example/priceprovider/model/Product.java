package com.example.priceprovider.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(
        UUID productUuid,
        String name,
        BigDecimal basePrice
) {

}
