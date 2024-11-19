package com.homework.rewardpoints.reward.dto.response;

import com.homework.rewardpoints.reward.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String productName;
    private Integer price;
    private String category;
}
