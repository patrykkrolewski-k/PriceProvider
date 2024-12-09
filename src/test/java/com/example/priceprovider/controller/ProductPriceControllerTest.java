package com.example.priceprovider.controller;

import com.example.priceprovider.model.DiscountPolicy;
import com.example.priceprovider.model.ProductOrder;
import com.example.priceprovider.request.ProductPriceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest
@ComponentScan(basePackages = "com.example.priceprovider.service.impl")
public class ProductPriceControllerTest {
    private final static String UUID_STRING = "c5ebfecd-36c0-42d4-b4e9-a93e65b58a56";
    private final static UUID TEST_UUID = UUID.fromString(UUID_STRING);


    private static final ProductPriceRequest PRICE_REQUEST = new ProductPriceRequest(List.of(
            new ProductOrder(TEST_UUID, 1, DiscountPolicy.AMOUNT_BASED)));

    @Autowired
    private MockMvc mvc;

    @Test
    public void singlePrice() throws Exception {
        String content = convertObjectToJsonString(PRICE_REQUEST);
        mvc.perform(get("/price")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("discountBids[0].product.name").value("product name1")).andReturn();
    }

    private String convertObjectToJsonString(ProductPriceRequest request) throws JsonProcessingException {
        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return mapper.writeValueAsString(request);
    }

}
