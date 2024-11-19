package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.TransactionRequest;
import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.service.RewardPointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-points")
@RequiredArgsConstructor
public class RewardPointsController {

    private final RewardPointsService rewardPointsService;

    @GetMapping("/get-summary")
    @ResponseStatus(HttpStatus.OK)
    public List<RewardsSummaryResponse> getRewardsSummary(@RequestBody List<TransactionRequest> transactionRequests){
        return rewardPointsService.getRewardsSummary(transactionRequests);
    }
}
