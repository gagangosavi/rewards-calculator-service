package com.homework.rewardpoints.reward.service.utility.monthlyrewards;

import com.homework.rewardpoints.reward.dto.request.CustomersAndMonthsRequest;
import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.exception.customer.CustomerNotFoundException;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Purchase;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.PurchaseRepository;
import com.homework.rewardpoints.reward.service.utility.rewardssummary.GenerateRewardsSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlyRewardsCalculatorService {
    private final PurchaseRepository purchaseRepository;
    private final GenerateRewardsSummaryResponse generateRewardsSummaryResponse;
    private final CustomerRepository customerRepository;

    public List<RewardsSummaryResponse> getThreeMonthlyRewardsByCustomers(List<Customer> customers){
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(3);
        List<Long> customerIds = customers.stream().map(customer -> customer.getCustomerId()).collect(Collectors.toList());
        List<Purchase> purchases = purchaseRepository.findByCustomerIdsInPurchaseTimeBetween(customerIds, startDate, endDate);
        //create a list of last 3 months
        List<Month> months = List.of(endDate.getMonth(), endDate.minusMonths(1).getMonth(), endDate.minusMonths(2).getMonth());

        //Pre-processing the customer to purchase relationship for faster access in creating monthly rewards map
        Map<Long,List<Purchase>> customerToPurchasesMap = purchases.stream().collect(Collectors.groupingBy(purchase -> purchase.getCustomer().getCustomerId()));
        return generateRewardsSummaryResponse.generateRewardsSummaryResponseForGivenCustomersAndPurchases(customers, months, customerToPurchasesMap);
    }

    public List<RewardsSummaryResponse> getRewardsForGivenMonthsForAllCustomers(List<Month> months){

        List<Integer> monthNumbers = months.stream().map(Month::getValue).collect(Collectors.toList());

        List<Purchase> purchases = purchaseRepository.findAllByMonths(monthNumbers);

        List<Long> customerIds = purchases.stream().map(purchase -> purchase.getCustomer().getCustomerId()).collect(Collectors.toList());

        List<Customer> customers = customerRepository.findAllById(customerIds);

        //Pre-processing the customer to purchase relationship for faster access in creating monthly rewards map
        Map<Long,List<Purchase>> customerToPurchasesMap = purchases.stream().collect(Collectors.groupingBy(purchase -> purchase.getCustomer().getCustomerId()));

        return generateRewardsSummaryResponse.generateRewardsSummaryResponseForGivenCustomersAndPurchases(customers,months,customerToPurchasesMap);

    }

    public List<RewardsSummaryResponse> getRewardsForGivenCustomersForGivenMonths(CustomersAndMonthsRequest customersAndMonthsRequest){

        List<Customer> customers = customerRepository.findAllById(customersAndMonthsRequest.getCustomerIds());

        List<Integer> monthNumbers = customersAndMonthsRequest.getMonths().stream().map(Month::getValue).collect(Collectors.toList());

        List<Purchase> purchases = purchaseRepository.findAllByMonths(monthNumbers);

        //Pre-processing the customer to purchase relationship for faster access in creating monthly rewards map
        Map<Long,List<Purchase>> customerToPurchasesMap = purchases.stream().collect(Collectors.groupingBy(purchase -> purchase.getCustomer().getCustomerId()));

        return generateRewardsSummaryResponse.generateRewardsSummaryResponseForGivenCustomersAndPurchases(customers,customersAndMonthsRequest.getMonths(),customerToPurchasesMap);
    }
}
