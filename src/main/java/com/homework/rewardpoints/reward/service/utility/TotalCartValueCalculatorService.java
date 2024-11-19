package com.homework.rewardpoints.reward.service.utility;

import com.homework.rewardpoints.reward.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalCartValueCalculatorService {
    public Double calculateTotalCartValue(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
