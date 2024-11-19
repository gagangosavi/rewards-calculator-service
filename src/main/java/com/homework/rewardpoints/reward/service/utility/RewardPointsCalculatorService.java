package com.homework.rewardpoints.reward.service.utility;

import org.springframework.stereotype.Service;

@Service
public class RewardPointsCalculatorService {
    public Long calculateTotalRewardPointsForThePurchase(Double totalCartValue) {
        long totalCartValueAsLong = totalCartValue.longValue();
        long totalRewardPoints = 0;

        if(totalCartValueAsLong > 100){
            totalRewardPoints = (2*(totalCartValueAsLong-100))+50;

        }
        else if(totalCartValueAsLong > 50){
            totalRewardPoints = (totalCartValueAsLong - 50);
        }
        return totalRewardPoints;
    }
}
