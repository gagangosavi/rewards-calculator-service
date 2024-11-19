package com.homework.rewardpoints.reward.service.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardPointsCalculatorServiceTest {
    private final RewardPointsCalculatorService service = new RewardPointsCalculatorService();

    @Test
    void testCalculateTotalRewardPointsForThePurchase_above100() {
        Double totalCartValue = 120.0;
        Long rewardPoints = service.calculateTotalRewardPointsForThePurchase(totalCartValue);
        assertEquals(90L, rewardPoints);
    }

    @Test
    void testCalculateTotalRewardPointsForThePurchase_between50And100() {
        Double totalCartValue = 70.0;
        Long rewardPoints = service.calculateTotalRewardPointsForThePurchase(totalCartValue);
        assertEquals(20L, rewardPoints);
    }

    @Test
    void testCalculateTotalRewardPointsForThePurchase_below50() {
        Double totalCartValue = 40.0;
        Long rewardPoints = service.calculateTotalRewardPointsForThePurchase(totalCartValue);
        assertEquals(0L, rewardPoints);
    }
}
