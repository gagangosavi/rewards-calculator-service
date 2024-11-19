package com.homework.rewardpoints.reward.dto.response;

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
public class PurchaseResponse {
    private LocalDateTime purchaseTime;
    private CustomerResponse customerResponse;
    private List<ProductResponse> productResponses;
    private double purchaseAmount;
    private long totalRewardPoints;
}
