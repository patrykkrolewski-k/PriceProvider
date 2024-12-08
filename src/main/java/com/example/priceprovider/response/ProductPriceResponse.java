package com.example.priceprovider.response;

import com.example.priceprovider.model.DiscountBid;

import java.util.List;

public record ProductPriceResponse(
        List<DiscountBid> discountBids
) {

}

