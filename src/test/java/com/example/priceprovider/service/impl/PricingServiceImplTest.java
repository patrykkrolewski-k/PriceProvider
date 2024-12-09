package com.example.priceprovider.service.impl;

import com.example.priceprovider.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BasePriceServiceImplTest {

    private BasePriceServiceImpl basePriceService;

    private static final UUID PRODUCT_UUID = UUID.fromString("31fa9e08-1eaa-4e95-945e-ebc5bb7cde35");
    private static final Product TEST_PRODUCT = new Product(
            PRODUCT_UUID,
            "product name3",
            BigDecimal.valueOf(0.29)
    );

    @BeforeEach
    void setUp() throws IOException {
        basePriceService = new BasePriceServiceImpl() {
            @Override
            public Map<UUID, Product> getProductMap() {
                return productMap;
            }
        };
    }

    @Test
    void verifyGetBaseOffer_ValidUUID() {
        Product product = basePriceService.getBaseOffer(PRODUCT_UUID);

        assertNotNull(product);
        assertEquals(TEST_PRODUCT, product);
    }

    @Test
    void verifyGetBaseOffer_InvalidUUID() {
        UUID invalidUUID = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            basePriceService.getBaseOffer(invalidUUID);
        });

        assertEquals("there is no product with uuid: " + invalidUUID.toString(), exception.getMessage());
    }

    @Test
    void verifyGetProductMap() {
        Map<UUID, Product> productMap = basePriceService.getProductMap();
        assertNotNull(productMap);
        assertTrue(productMap.containsKey(PRODUCT_UUID));
        assertEquals(TEST_PRODUCT, productMap.get(PRODUCT_UUID));
    }
}
