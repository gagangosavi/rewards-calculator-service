package com.homework.rewardpoints.reward.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardsSummaryResponse {
    private String customerFirstName;
    private String customerLastName;
    private Map<Month,Long> monthlyRewards;
    private long totalRewards;
}
