package com.homework.rewardpoints.reward.dto.request;

import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    private List<Product> products;
    private Customer customer;
    private LocalDateTime purchaseTime;
}
