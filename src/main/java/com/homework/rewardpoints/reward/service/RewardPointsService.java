package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.TransactionRequest;
import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Purchase;
import com.homework.rewardpoints.reward.model.RewardPoints;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.PurchaseRepository;
import com.homework.rewardpoints.reward.repository.RewardPointsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardPointsService {
    private final RewardPointsRepository rewardPointsRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    public void addRewardPoints(RewardPoints rewardPoints){
        rewardPointsRepository.save(rewardPoints);
    }

    public List<RewardsSummaryResponse> getRewardsSummary(List<TransactionRequest> transactionRequests){
        Map<Long,Customer> customerMap = customerRepository.findAllById(transactionRequests
                .stream()
                .map(transactionRequest -> transactionRequest
                        .getCustomerId())
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Customer::getCustomerId, Function.identity()));

        Map<Long,Purchase> purchaseMap = purchaseRepository.findAllById(transactionRequests
                .stream()
                .map(transactionRequest -> transactionRequest
                        .getPurchaseId())
                .collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Purchase::getPurchaseId,Function.identity()));

        Map<Long,Map<Month,Long>> customerMonthlyRewards = new HashMap<>();
        Map<Long,Long> customerTotalRewards = new HashMap<>();

        for(TransactionRequest transactionRequest : transactionRequests){
            Customer customer = customerMap.get(transactionRequest.getCustomerId());
            Purchase purchase = purchaseMap.get(transactionRequest.getPurchaseId());

            if(customer == null || purchase == null){
                continue;
            }
            Month month = purchase.getRewardPoints().getEarnedDate().getMonth();
            long points = purchase.getRewardPoints().getRewardsEarned();

            customerMonthlyRewards.computeIfAbsent(customer.getCustomerId() , k -> new HashMap<>()).merge(month,points,Long::sum);
            customerTotalRewards.merge(customer.getCustomerId(),points,Long::sum);
        }

        List<RewardsSummaryResponse> rewardsSummaryResponses = new ArrayList<>();

        for(Long customerId : customerMonthlyRewards.keySet()){
            Customer customer = customerMap.get(customerId);
            Map<Month,Long> monthlyRewards = customerMonthlyRewards.get(customerId);
            long totalRewardPoints = customerTotalRewards.get(customerId);

            rewardsSummaryResponses.add(RewardsSummaryResponse.builder()
                    .totalRewards(totalRewardPoints)
                    .monthlyRewards(monthlyRewards)
                    .customerFirstName(customer.getFirstName())
                    .customerLastName(customer.getLastName())
                    .build());
        }

        return rewardsSummaryResponses;
    }
}
