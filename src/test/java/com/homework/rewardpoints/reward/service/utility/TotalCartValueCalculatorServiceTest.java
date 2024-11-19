package com.homework.rewardpoints.reward.service.utility;

import com.homework.rewardpoints.reward.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalCartValueCalculatorServiceTest {
    private final TotalCartValueCalculatorService service = new TotalCartValueCalculatorService();

    @Test
    void testCalculateTotalCartValue() {
        List<Product> products = Arrays.asList(
                Product.builder().price(100).build(),
                Product.builder().price(200).build(),
                Product.builder().price(50).build()
        );

        Double totalValue = service.calculateTotalCartValue(products);
        assertEquals(350.0, totalValue);
    }
}
