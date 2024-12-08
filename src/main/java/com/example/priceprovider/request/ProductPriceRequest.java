package com.example.priceprovider.request;

import com.example.priceprovider.model.ProductOrder;

import java.util.List;

public record ProductPriceRequest(
        List<ProductOrder> orderList
) {


}
