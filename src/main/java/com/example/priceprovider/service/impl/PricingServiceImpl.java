package com.example.priceprovider.service.impl;

import com.example.priceprovider.model.DiscountBid;
import com.example.priceprovider.model.Product;
import com.example.priceprovider.model.ProductOrder;
import com.example.priceprovider.service.BasePriceService;
import com.example.priceprovider.service.PricingService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@ConfigurationProperties(prefix = "discount")
public class PricingServiceImpl implements PricingService {
    private Map<Integer, Double> amountbased;
    private Map<Integer, Double> percentagebased;

    private TreeSet<Integer> amountTresholds;
    private TreeSet<Integer> percentageTresholds;

    @Autowired
    BasePriceService basePriceService;

    @PostConstruct
    public void init() {
        if (amountbased != null) {
            amountTresholds = new TreeSet<>(amountbased.keySet());
        } else {
            throw new IllegalStateException("amountbased is not initialized");
        }
        if (percentagebased != null) {
            percentageTresholds = new TreeSet<>(percentagebased.keySet());
        } else {
            throw new IllegalStateException("percentagebased is not initialized");
        }
    }

    @Override
    public List<DiscountBid> createBid(List<ProductOrder> orderList) {
        return orderList.stream()
                .map(o -> createBidForProductOrder(o))
                .collect(Collectors.toList());
    }

    private DiscountBid createBidForProductOrder(ProductOrder order) {
        Product product = basePriceService.getBaseOffer(order.productUuid());
        BigDecimal discount = calculateDiscount(order, product);
        BigDecimal offeredPrice = product.basePrice().subtract(discount);

        if (offeredPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException(" Price cannot be negative");
        }
        return new DiscountBid(
                product,
                order.discountPolicy(),
                order.amount(),
                offeredPrice.setScale(2)
        );
    }


    private BigDecimal calculateDiscount(ProductOrder order, Product product) {
        switch (order.discountPolicy()) {
            case AMOUNT_BASED:
                return amountBasedDiscount(order, product);
            case PERCENTAGE_BASED:
                return percentageBasedDiscount(order, product);
            default:
                return BigDecimal.ZERO;
        }
    }


    BigDecimal amountBasedDiscount(ProductOrder productOrder, Product product) {
        Integer treshold = amountTresholds.lower(productOrder.amount() + 1);
        if (treshold != null) {
            return BigDecimal.valueOf(amountbased.get(treshold));
        }
        return BigDecimal.valueOf(0);
    }

    BigDecimal percentageBasedDiscount(ProductOrder productOrder, Product product) {
        Integer treshold = percentageTresholds.lower(productOrder.amount() + 1);
        if (treshold != null) {
            return BigDecimal.valueOf(percentagebased.get(treshold)).multiply(product.basePrice());
        }
        return BigDecimal.valueOf(0);
    }


    void setAmountbased(Map<Integer, Double> amountbased) {
        this.amountbased = amountbased;
    }

    void setPercentagebased(Map<Integer, Double> percentagebased) {
        this.percentagebased = percentagebased;
    }
}
