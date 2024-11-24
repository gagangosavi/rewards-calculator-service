package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.TransactionRequest;
import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.model.Purchase;
import com.homework.rewardpoints.reward.model.RewardPoints;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.PurchaseRepository;
import com.homework.rewardpoints.reward.repository.RewardPointsRepository;
import com.homework.rewardpoints.reward.service.utility.foeachpurchase.RewardPointsCalculatorService;
import com.homework.rewardpoints.reward.service.utility.foeachpurchase.TotalCartValueCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class RewardPointsServiceTest {

//    @Mock
//    private RewardPointsRepository rewardPointsRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private PurchaseRepository purchaseRepository;
//
//    @InjectMocks
//    private RewardPointsService rewardPointsService;
//
//    @InjectMocks
//    private RewardPointsCalculatorService rewardPointsCalculatorService;
//
//    @InjectMocks
//    private TotalCartValueCalculatorService totalCartValueCalculatorService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getRewardsSummary_success() {
//        // Mock input data
//        List<TransactionRequest> transactionRequests = Arrays.asList(
//                new TransactionRequest(1L, 101L),
//                new TransactionRequest(2L, 102L)
//        );
//
//        List<Customer> customers = Arrays.asList(
//                Customer.builder().customerId(1L).firstName("John").lastName("Doe").contact("8877600886").email("johndoe@email").totalRewardPoints(0L).build(),
//                Customer.builder().customerId(2L).firstName("Jane").lastName("Smith").contact("8877600536").email("janesmith@email").totalRewardPoints(0L).build()
//        );
//
//        List<Product> products = Arrays.asList(Product.builder()
//                .productId(1L)
//                .price(300.0)
//                .productName("Shampoo")
//                .build(),
//                Product.builder()
//                        .productId(2L)
//                        .price(150.0)
//                        .productName("Coffee")
//                        .build());
//
//        Double totalCartValue = totalCartValueCalculatorService.calculateTotalCartValue(products);
//
//        Long totalPointsEarned = rewardPointsCalculatorService.calculateTotalRewardPointsForThePurchase(totalCartValue);
//
//        List<Purchase> purchases = Arrays.asList(
//                Purchase.builder().purchaseId(101L)
//                        .rewardPoints(RewardPoints.builder()
//                                .rewardId(50L)
//                                .earnedDate(LocalDateTime.of(2024, Month.NOVEMBER, 10, 10, 0))
//                                .rewardsEarned(totalPointsEarned)
//                                .build())
//                        .purchaseAmount(450.0)
//                        .customer(customers.get(0))
//                        .products(products)
//                        .build(),
//                Purchase.builder().purchaseId(102L)
//                        .rewardPoints(RewardPoints.builder()
//                                .rewardId(51L)
//                                .earnedDate(LocalDateTime.of(2024, Month.OCTOBER, 10, 10, 0))
//                                .rewardsEarned(totalPointsEarned)
//                                .build())
//                        .purchaseAmount(450.0)
//                        .customer(customers.get(1))
//                        .products(products)
//                        .build());
//
//        when(customerRepository.findAllById(Arrays.asList(1L, 2L)))
//                .thenReturn(customers);
//        when(purchaseRepository.findAllById(Arrays.asList(101L, 102L)))
//                .thenReturn(purchases);
//
//        List<RewardsSummaryResponse> result = rewardPointsService.getRewardsSummary(transactionRequests);
//
//        // Assertions
//        assertEquals(2, result.size());
//
//        // Verify the first customer
//        RewardsSummaryResponse response1 = result.get(0);
//        assertEquals("John", response1.getCustomerFirstName());
//        assertEquals("Doe", response1.getCustomerLastName());
//        assertEquals(750L, response1.getTotalRewards());
//        assertEquals(1, response1.getMonthlyRewards().size());
//        assertEquals(750L, response1.getMonthlyRewards().get(Month.NOVEMBER));
//
//        // Verify the second customer
//        RewardsSummaryResponse response2 = result.get(1);
//        assertEquals("Jane", response2.getCustomerFirstName());
//        assertEquals("Smith", response2.getCustomerLastName());
//        assertEquals(750L, response2.getTotalRewards());
//        assertEquals(1, response2.getMonthlyRewards().size());
//        assertEquals(750L, response2.getMonthlyRewards().get(Month.NOVEMBER));
//
//        // Verify repository interactions
//        verify(customerRepository, times(1)).findAllById(Arrays.asList(1L, 2L));
//        verify(purchaseRepository, times(1)).findAllById(Arrays.asList(101L, 102L));
//        verifyNoInteractions(rewardPointsRepository); // This method does not use rewardPointsRepository.
//    }
}
