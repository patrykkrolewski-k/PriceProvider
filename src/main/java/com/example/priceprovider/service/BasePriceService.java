package com.example.priceprovider.service;

import com.example.priceprovider.model.Product;

import java.util.UUID;

public interface BasePriceService {

    Product getBaseOffer(UUID uuid);

}
