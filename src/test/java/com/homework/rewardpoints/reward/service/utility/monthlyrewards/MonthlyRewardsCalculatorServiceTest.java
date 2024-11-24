package com.homework.rewardpoints.reward.service.utility.monthlyrewards;

import com.homework.rewardpoints.reward.dto.response.RewardsSummaryResponse;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.model.Purchase;
import com.homework.rewardpoints.reward.model.RewardPoints;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.PurchaseRepository;
import com.homework.rewardpoints.reward.service.utility.foeachpurchase.RewardPointsCalculatorService;
import com.homework.rewardpoints.reward.service.utility.foeachpurchase.TotalCartValueCalculatorService;
import com.homework.rewardpoints.reward.service.utility.rewardssummary.GenerateRewardsSummaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonthlyRewardsCalculatorServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GenerateRewardsSummaryResponse generateRewardsSummaryResponse;

    @InjectMocks
    private MonthlyRewardsCalculatorService monthlyRewardsCalculatorService;

    @InjectMocks
    private TotalCartValueCalculatorService totalCartValueCalculatorService;

    @InjectMocks
    private RewardPointsCalculatorService rewardPointsCalculatorService;

    List<Customer> customers;
    List<Product> products;

    List<Purchase> purchases;

    List<Month> months;
    List<RewardsSummaryResponse> expectedResponse;
    @BeforeEach
    void setUp(){
        customers = Arrays.asList(
                Customer.builder().customerId(1L).firstName("John").lastName("Doe").contact("8877600886").email("johndoe@email").totalRewardPoints(0L).build(),
                Customer.builder().customerId(2L).firstName("Jane").lastName("Smith").contact("8877600536").email("janesmith@email").totalRewardPoints(0L).build()
        );

        products = Arrays.asList(Product.builder()
                        .productId(1L)
                        .price(300.0)
                        .productName("Shampoo")
                        .build(),
                Product.builder()
                        .productId(2L)
                        .price(150.0)
                        .productName("Coffee")
                        .build());

        Double totalCartValue = totalCartValueCalculatorService.calculateTotalCartValue(products);

        Long totalPointsEarned = rewardPointsCalculatorService.calculateTotalRewardPointsForThePurchase(totalCartValue);

        purchases = Arrays.asList(
                Purchase.builder().purchaseId(101L)
                        .rewardPoints(RewardPoints.builder()
                                .rewardId(50L)
                                .earnedDate(LocalDateTime.of(2024, Month.NOVEMBER, 10, 10, 0))
                                .rewardsEarned(totalPointsEarned)
                                .build())
                        .purchaseAmount(450.0)
                        .customer(customers.get(0))
                        .products(products)
                        .build(),
                Purchase.builder().purchaseId(102L)
                        .rewardPoints(RewardPoints.builder()
                                .rewardId(51L)
                                .earnedDate(LocalDateTime.of(2024, Month.OCTOBER, 10, 10, 0))
                                .rewardsEarned(totalPointsEarned)
                                .build())
                        .purchaseAmount(450.0)
                        .customer(customers.get(1))
                        .products(products)
                        .build(),
                Purchase.builder().purchaseId(101L)
                        .rewardPoints(RewardPoints.builder()
                                .rewardId(51L)
                                .earnedDate(LocalDateTime.of(2024, Month.SEPTEMBER, 10, 10, 0))
                                .rewardsEarned(totalPointsEarned)
                                .build())
                        .purchaseAmount(450.0)
                        .customer(customers.get(1))
                        .products(products)
                        .build(),
                Purchase.builder().purchaseId(101L)
                        .rewardPoints(RewardPoints.builder()
                                .rewardId(51L)
                                .earnedDate(LocalDateTime.of(2024, Month.JULY, 10, 10, 0))
                                .rewardsEarned(totalPointsEarned)
                                .build())
                        .purchaseAmount(450.0)
                        .customer(customers.get(1))
                        .products(products)
                        .build());

        Map<Month,Long> monthlyRewardsForCustomerOne = new HashMap<>();
        monthlyRewardsForCustomerOne.put(Month.NOVEMBER,750L);
        monthlyRewardsForCustomerOne.put(Month.SEPTEMBER,750L);
        monthlyRewardsForCustomerOne.put(Month.OCTOBER,0L);

        Map<Month,Long> monthlyRewardsForCustomerTwo = new HashMap<>();
        monthlyRewardsForCustomerTwo.put(Month.NOVEMBER,0L);
        monthlyRewardsForCustomerTwo.put(Month.OCTOBER,750L);
        monthlyRewardsForCustomerTwo.put(Month.SEPTEMBER,0L);


        expectedResponse = Arrays.asList(RewardsSummaryResponse.builder()
                .customerFirstName(customers.get(0).getFirstName())
                .customerLastName(customers.get(0).getLastName())
                .totalRewards(1500L)
                .monthlyRewards(monthlyRewardsForCustomerOne)
                .build(),
                RewardsSummaryResponse.builder()
                        .customerFirstName(customers.get(1).getFirstName())
                        .customerLastName(customers.get(1).getLastName())
                        .totalRewards(750L)
                        .monthlyRewards(monthlyRewardsForCustomerTwo)
                        .build());
        LocalDateTime startDate = LocalDateTime.now();
        months = List.of(startDate.getMonth(),startDate.minusMonths(1).getMonth(),startDate.minusMonths(2).getMonth());
    }

    @Test
    void getThreeMonthlyRewardsByCustomers_Success() {
        // Arrange
        when(purchaseRepository.findByCustomerIdsInPurchaseTimeBetween(any(), any(), any()))
                .thenReturn(purchases);
        when(generateRewardsSummaryResponse.generateRewardsSummaryResponseForGivenCustomersAndPurchases(
                any(), any(), any())).thenReturn(expectedResponse);

        // Act
        List<RewardsSummaryResponse> result = monthlyRewardsCalculatorService
                .getThreeMonthlyRewardsByCustomers(customers);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(purchaseRepository).findByCustomerIdsInPurchaseTimeBetween(any(), any(), any());
        verify(generateRewardsSummaryResponse).generateRewardsSummaryResponseForGivenCustomersAndPurchases(
                any(), any(), any());
    }



}
