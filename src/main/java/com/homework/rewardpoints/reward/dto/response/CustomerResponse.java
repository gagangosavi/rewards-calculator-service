package com.homework.rewardpoints.reward.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String contact;

    private long totalRewardPoints;
}
