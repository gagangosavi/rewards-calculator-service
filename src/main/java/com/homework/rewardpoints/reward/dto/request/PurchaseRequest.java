package com.homework.rewardpoints.reward.dto.request;

import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Product list cannot be null")
    @Size(min = 1 , message = "At least one product must be added for the purchase")
    private List<Product> products;

    @NotNull(message = "Customer details must not be null")
    private Customer customer;

    private LocalDateTime purchaseTime;
}
