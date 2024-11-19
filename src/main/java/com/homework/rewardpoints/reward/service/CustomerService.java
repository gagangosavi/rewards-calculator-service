package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.exception.customer.InvalidCustomerRequestException;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest){

        if(customerRequest.getFirstName() == null || customerRequest.getFirstName().isEmpty()){
            throw new InvalidCustomerRequestException("First Name cannot be null or empty");
        }

        if(customerRequest.getLastName() == null || customerRequest.getLastName().isEmpty()){
            throw new InvalidCustomerRequestException("Last Name cannot be null or empty");
        }

        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .contact(customerRequest.getContact())
                .build();
        try {
            customerRepository.save(customer);
        }catch(Exception e){
            log.error("Error saving customer: {}", e.getMessage());
            throw new DatabaseOperationException("Failed to save customer to the database", e);
        }
        log.info("Customer added successfully");
        return customer.toCustomerResponse();
    }

    public List<CustomerResponse> getAllCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(Customer::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public Set<CustomerResponse> addCustomersInBulk(List<CustomerRequest> customerRequests){
        Set<Customer> customers = new HashSet<>();
        for(CustomerRequest customerRequest : customerRequests){
            Customer customer = Customer.builder()
                    .firstName(customerRequest.getFirstName())
                    .lastName(customerRequest.getLastName())
                    .email(customerRequest.getEmail())
                    .contact(customerRequest.getContact())
                    .build();
            customers.add(customer);
        }
        customerRepository.saveAll(customers);
        log.info("Customers added successfully");
        return customers.stream().map(Customer::toCustomerResponse).collect(Collectors.toSet());
    }
}
