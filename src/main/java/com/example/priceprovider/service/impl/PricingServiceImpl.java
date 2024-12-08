package com.example.priceprovider.service.impl;

import com.example.priceprovider.model.DiscountBid;
import com.example.priceprovider.model.Product;
import com.example.priceprovider.model.ProductOrder;
import com.example.priceprovider.service.PricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PricingServiceImpl implements PricingService {

    @Override
    public List<DiscountBid> createBid(List<ProductOrder> orderList) {

        List<DiscountBid> bids = new ArrayList<>();
        for (ProductOrder order : orderList) {
            bids.add(amountBasedBid(order));
        }

        return bids;
    }


    DiscountBid amountBasedBid(ProductOrder productOrder) {
        return new DiscountBid(
                new Product(productOrder.productUuid(),
                        "test-name",
                        BigDecimal.valueOf(0)),
                productOrder.discountPolicy(),
                productOrder.amount(),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(0));
    }
}
