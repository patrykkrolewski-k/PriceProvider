package com.example.priceprovider.service.impl;

import com.example.priceprovider.model.Product;
import com.example.priceprovider.service.BasePriceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
public class BasePriceServiceImpl implements BasePriceService {

    final Map<UUID, Product> productMap;

    public BasePriceServiceImpl() throws IOException {
        File file = new File("src/main/resources/basePrices.json");
        ObjectMapper objectMapper = new ObjectMapper();
        this.productMap = objectMapper.readValue(file, new TypeReference<Map<UUID, Product>>() {});

    }


    @Override
    public Product getBaseOffer(UUID uuid) {
        if (!productMap.containsKey(uuid)) {
            throw new IllegalArgumentException("there is no product with uuid: " + uuid.toString());
        }
        return productMap.get(uuid);
    }

    public Map<UUID, Product> getProductMap() {
        return productMap;
    }
}
