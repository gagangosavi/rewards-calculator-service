package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse addCustomer(@RequestBody CustomerRequest customerRequest){
        return customerService.addCustomer(customerRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/bulk-add")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<CustomerResponse> addCustomersInBulk(@RequestBody List<CustomerRequest> customerRequests){
        return customerService.addCustomersInBulk(customerRequests);
    }
}
