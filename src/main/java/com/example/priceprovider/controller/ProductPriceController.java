package com.example.priceprovider.controller;

import com.example.priceprovider.request.ProductPriceRequest;
import com.example.priceprovider.response.ProductPriceResponse;
import com.example.priceprovider.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class ProductPriceController {

    @Autowired
    PricingService pricingService;


    @GetMapping(consumes="application/json", produces = "application/json")
    public ProductPriceResponse getProductPrice(@RequestBody ProductPriceRequest request) {
        return new ProductPriceResponse(pricingService.createBid(request.orderList()));
    }
}
