package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CustomersAndMonthsRequest;
import com.homework.rewardpoints.reward.dto.request.TransactionRequest;
import com.homework.rewardpoints.reward.dto.response.ProductResponse;
import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.service.RewardPointsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/reward-points")
@RequiredArgsConstructor
public class RewardPointsController {

    private final RewardPointsService rewardPointsService;

//    @GetMapping("/get-summary")
//    @ResponseStatus(HttpStatus.OK)
//    public List<RewardsSummaryResponse> getRewardsSummary(@RequestBody List<TransactionRequest> transactionRequests){
//        return rewardPointsService.getRewardsSummary(transactionRequests);
//    }

    @GetMapping("/get-3months-summary")
    @ResponseStatus(HttpStatus.OK)
    public List<RewardsSummaryResponse> getThreeMonthsRewardSummary(){
        return rewardPointsService.getThreeMonthsRewardsFoAllCustomers();
    }

    @GetMapping("/get-summary-for-months")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRewardsForGivenMonthsForAllCustomers(@RequestBody
                                                                                    List<Month> months){
        if(months.isEmpty()){
            return new ResponseEntity<>("Please provide at least one month",HttpStatus.BAD_REQUEST);
        }
        List<RewardsSummaryResponse> rewardsSummaryResponses = rewardPointsService.getRewardsForGivenMonthsForAllCustomers(months);

        return new ResponseEntity<List<RewardsSummaryResponse>>(rewardsSummaryResponses,HttpStatus.OK);
    }

    @GetMapping("/get-summary-for-customers-and-months")
    @ResponseStatus(HttpStatus.OK)
    public List<RewardsSummaryResponse> getRewardsForGivenCustomersForGivenMonths(@RequestBody
                                                                                      @Valid CustomersAndMonthsRequest customersAndMonthsRequest){
        return rewardPointsService.getRewardsForGivenCustomersForGivenMonths(customersAndMonthsRequest);
    }

    @GetMapping("/get-3-months-summary-for-customers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getThreeMonthsRewardsForGivenCustomers(@RequestBody
                                                                                   List<Long> customerIds){
        if(customerIds.isEmpty()){
            return new ResponseEntity<>("Please provide at least one customer id",HttpStatus.BAD_REQUEST);
        }
        List<RewardsSummaryResponse> rewardsSummaryResponses = rewardPointsService.getThreeMonthsRewardsForGivenCustomers(customerIds);
        return  new ResponseEntity<List<RewardsSummaryResponse>>(rewardsSummaryResponses,HttpStatus.OK);
    }
}
