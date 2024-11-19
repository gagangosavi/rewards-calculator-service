package com.homework.rewardpoints.reward.service;
import com.homework.rewardpoints.reward.dto.request.PurchaseRequest;
import com.homework.rewardpoints.reward.dto.response.PurchaseResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.exception.customer.CustomerNotFoundException;
import com.homework.rewardpoints.reward.exception.product.ProductNotFoundException;
import com.homework.rewardpoints.reward.model.*;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.ProductRepository;
import com.homework.rewardpoints.reward.repository.PurchaseRepository;
import com.homework.rewardpoints.reward.service.utility.RewardPointsCalculatorService;
import com.homework.rewardpoints.reward.service.utility.TotalCartValueCalculatorService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PurchaseServiceTest {
    @InjectMocks
    private  PurchaseService purchaseService;

    @Mock
    private  PurchaseRepository purchaseRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private  RewardPointsCalculatorService rewardPointsCalculatorService;

    @Mock
    private  TotalCartValueCalculatorService totalCartValueCalculatorService;

    private PurchaseRequest purchaseRequest;
    private Customer customer;
    private List<Product> products;
    private Purchase purchase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        products = Arrays.asList(Product.builder().productName("product1").productId(1L).price(100).category(Category.builder().categoryName("Electronics").build()).build(),Product.builder().productName("product2").productId(2L).price(200).category(Category.builder().categoryName("Clothing").build()).build());
        customer = Customer.builder().firstName("John").lastName("Doe").customerId(1L).email("johndoe@email").contact("0099887665").build();
        purchaseRequest = PurchaseRequest.builder().customer(customer).products(products).purchaseTime(LocalDateTime.parse("2024-10-15T12:00:00")).build();

        RewardPoints rewardPoints = RewardPoints.builder().rewardId(1L).rewardsEarned(450).earnedDate(LocalDateTime.parse("2024-10-15T12:00:00")).build();
        purchase = Purchase.builder()
                .purchaseAmount(300.0)
                .rewardPoints(rewardPoints)
                .customer(customer)
                .products(products)
                .build();
    }

    @Test
    void makePurchase_successfulPurchase() {
        // Mock repository and service behavior
        when(productRepository.findAllById(anyList())).thenReturn(products);
        when(totalCartValueCalculatorService.calculateTotalCartValue(products)).thenReturn(300.0);
        when(rewardPointsCalculatorService.calculateTotalRewardPointsForThePurchase(300.0)).thenReturn(450L);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        // Call the service
        PurchaseResponse response = purchaseService.makePurchase(purchaseRequest);

        // Verify interactions and assert results
        verify(customerRepository).save(customer);
        verify(purchaseRepository).save(any(Purchase.class));
        assertEquals(300.0, response.getPurchaseAmount());
        assertEquals(450L, response.getTotalRewardPoints());
        assertEquals("John", response.getCustomerResponse().getFirstName());
    }

    @Test
    void makePurchase_customerNotFound() {
        // Mock customer repository behavior
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the service and expect exception
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> purchaseService.makePurchase(purchaseRequest));

        assertEquals("Customer Not Found", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }

    @Test
    void makePurchase_databaseOperationException() {
        // Mock repository and service behavior
        when(productRepository.findAllById(anyList())).thenReturn(products);
        when(totalCartValueCalculatorService.calculateTotalCartValue(products)).thenReturn(300.0);
        when(rewardPointsCalculatorService.calculateTotalRewardPointsForThePurchase(300.0)).thenReturn(450L);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        doThrow(RuntimeException.class).when(customerRepository).save(any(Customer.class));

        // Call the service and expect exception
        DatabaseOperationException exception = assertThrows(DatabaseOperationException.class,
                () -> purchaseService.makePurchase(purchaseRequest));

        assertEquals("Failed to make purchase", exception.getMessage());
        verify(customerRepository).save(customer);
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }

    @Test
    void getAllPurchases_successful() {
        // Mock repository behavior
        when(purchaseRepository.findAll()).thenReturn(Arrays.asList(purchase));

        // Call the service
        List<PurchaseResponse> responses = purchaseService.getAllPurchases();

        // Assert results
        assertEquals(1, responses.size());
        assertEquals(300.0, responses.get(0).getPurchaseAmount());
        assertEquals("John", responses.get(0).getCustomerResponse().getFirstName());
    }
}
