package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.dto.request.ProductRequest;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import com.homework.rewardpoints.reward.dto.response.ProductResponse;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.service.CustomerService;
import com.homework.rewardpoints.reward.service.ProductService;
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
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController{

    private final ProductService productService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addCustomer(@RequestBody
                                           @Valid ProductRequest productRequest){
        return productService.addProduct(productRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/bulk-add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProductsInBulk(@RequestBody
                                                      List<ProductRequest> productRequests){
        if(productRequests.isEmpty()){
            return new ResponseEntity<>("Please provide at least one product to make a purchase",HttpStatus.BAD_REQUEST);
        }
        Set<ProductResponse> productResponses = productService.addProductsInBulk(productRequests);
        return new  ResponseEntity<Set<ProductResponse>>(productResponses,HttpStatus.CREATED);
    }
}
