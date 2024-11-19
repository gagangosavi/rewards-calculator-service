package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.exception.customer.InvalidCustomerRequestException;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CustomerServiceTest {
    @InjectMocks
    private  CustomerService customerService;

    @Mock
    private  CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer_validRequest() {
        CustomerRequest request = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contact("1234567890")
                .build();

        Customer savedCustomer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contact("1234567890")
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerResponse response = customerService.addCustomer(request);

        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testAddCustomer_invalidRequest_missingFirstName() {
        CustomerRequest request = CustomerRequest.builder()
                .lastName("Doe")
                .email("john.doe@example.com")
                .contact("1234567890")
                .build();

        assertThrows(InvalidCustomerRequestException.class, () -> customerService.addCustomer(request));
        verifyNoInteractions(customerRepository);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().firstName("John").lastName("Doe").build(),
                Customer.builder().firstName("Jane").lastName("Smith").build()
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerResponse> responses = customerService.getAllCustomers();

        assertEquals(2, responses.size());
        assertEquals("John", responses.get(0).getFirstName());
        assertEquals("Jane", responses.get(1).getFirstName());
    }

    @Test
    void testAddCustomer_throwsDatabaseOperationException() {
        CustomerRequest request = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contact("1234567890")
                .build();

        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DatabaseOperationException.class, () -> customerService.addCustomer(request));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
