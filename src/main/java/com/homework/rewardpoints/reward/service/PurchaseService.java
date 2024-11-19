package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.PurchaseRequest;
import com.homework.rewardpoints.reward.dto.response.ProductResponse;
import com.homework.rewardpoints.reward.dto.response.PurchaseResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.exception.customer.CustomerNotFoundException;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.model.Purchase;
import com.homework.rewardpoints.reward.model.RewardPoints;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.ProductRepository;
import com.homework.rewardpoints.reward.repository.PurchaseRepository;
import com.homework.rewardpoints.reward.service.utility.RewardPointsCalculatorService;
import com.homework.rewardpoints.reward.service.utility.TotalCartValueCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final RewardPointsCalculatorService rewardPointsCalculatorService;
    private final TotalCartValueCalculatorService totalCartValueCalculatorService;

    public PurchaseResponse makePurchase(PurchaseRequest purchaseRequest){
        List<Product> products = productRepository.findAllById(purchaseRequest.getProducts().stream().map(product -> product.getProductId()).collect(Collectors.toList()));
        Double totalCartValue = totalCartValueCalculatorService.calculateTotalCartValue(products);
        Long totalRewardPointsForThePurchase = rewardPointsCalculatorService.calculateTotalRewardPointsForThePurchase(totalCartValue);
        Customer customer = customerRepository.findById(purchaseRequest.getCustomer().getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
        RewardPoints rewardPoints = RewardPoints.builder()
                .rewardsEarned(totalRewardPointsForThePurchase)
                .earnedDate(purchaseRequest.getPurchaseTime())
                .build();

            customer.setTotalRewardPoints(customer.getTotalRewardPoints()+totalRewardPointsForThePurchase);
        Purchase purchase = Purchase.builder()
                .purchaseAmount(totalCartValue)
                .rewardPoints(rewardPoints)
                .customer(customer)
                .products(products)
                .purchaseTime(purchaseRequest.getPurchaseTime())
                .build();

        try{
            customerRepository.save(customer);
            purchaseRepository.save(purchase);
        }
        catch (Exception e){
            log.error("Error making purchase : {}", e.getMessage());
            throw new DatabaseOperationException("Failed to make purchase", e);
        }

        log.info("Purchase made successfully");
        return purchase.toPurchaseResponse();
    }





    public List<PurchaseResponse> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(Purchase::toPurchaseResponse)
                .collect(Collectors.toList());
    }
}
