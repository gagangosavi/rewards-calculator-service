package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.PurchaseRequest;
import com.homework.rewardpoints.reward.dto.response.PurchaseResponse;
import com.homework.rewardpoints.reward.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/makePurchase")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponse makePurchase(@RequestBody PurchaseRequest purchaseRequest){
         return purchaseService.makePurchase(purchaseRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseResponse> getAllPurchases(){
        return purchaseService.getAllPurchases();
    }
}
