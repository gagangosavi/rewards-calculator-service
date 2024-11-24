package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.dto.response.CategoryResponse;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CustomerResponse addCustomer(@RequestBody
                                            @Valid CustomerRequest customerRequest){
        return customerService.addCustomer(customerRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/bulk-add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCustomersInBulk(@RequestBody
                                                        List<CustomerRequest> customerRequests){
        if(customerRequests.isEmpty()){
            return new ResponseEntity<>("Please provide at least one customer to be added",HttpStatus.BAD_REQUEST);
        }
        Set<CustomerResponse> customerResponses = customerService.addCustomersInBulk(customerRequests);
        return new  ResponseEntity<Set<CustomerResponse>>(customerResponses,HttpStatus.CREATED);
    }
}
