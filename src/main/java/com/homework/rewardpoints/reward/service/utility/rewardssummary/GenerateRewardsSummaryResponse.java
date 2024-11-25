package com.homework.rewardpoints.reward.service.utility.rewardssummary;

import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Purchase;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenerateRewardsSummaryResponse {

    public List<RewardsSummaryResponse> generateRewardsSummaryResponseForGivenCustomersAndPurchases(List<Customer> customers, List<Month> months, Map<Long, List<Purchase>> customerToPurchasesMap){
        return customers.stream().map(customer -> {

            //gets all purchases made by the customer
            List<Purchase> customerPurchases = customerToPurchasesMap.getOrDefault(customer.getCustomerId(),List.of());

            //create a Map to track rewards per month
            Map<Month,Long> monthlyRewards = customerPurchases.stream()
                    .collect(Collectors.toMap(
                            purchase -> purchase.getPurchaseTime().getMonth(),            // Month of the date the rewards were awarded
                            purchase -> purchase.getRewardPoints().getRewardsEarned(),    // The reward points that were awarded for the purchase
                            Long::sum,                                                     //Merging(summing) the reward points for the same month
                            () -> months.stream()
                                    .collect(Collectors.toMap(month -> month, month -> 0L)) //Creating Initial map with all month's reward point set to 0.
                    ));
            long totalRewards = monthlyRewards.values().stream().mapToLong(Long::longValue).sum();
            return RewardsSummaryResponse.builder()
                    .totalRewards(totalRewards)
                    .customerFirstName(customer.getFirstName())
                    .customerLastName(customer.getLastName())
                    .monthlyRewards(monthlyRewards)
                    .build();
        }).collect(Collectors.toList());
    }
}
