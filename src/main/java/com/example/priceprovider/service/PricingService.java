package com.example.priceprovider.service;

import com.example.priceprovider.model.DiscountBid;
import com.example.priceprovider.model.ProductOrder;

import java.util.List;
public interface PricingService {
    List<DiscountBid> createBid(List<ProductOrder> orderList);
}
