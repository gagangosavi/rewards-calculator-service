package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.dto.request.ProductRequest;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import com.homework.rewardpoints.reward.dto.response.ProductResponse;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.service.CustomerService;
import com.homework.rewardpoints.reward.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ProductResponse addCustomer(@RequestBody ProductRequest productRequest){
        return productService.addProduct(productRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/bulk-add")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<ProductResponse> addProductsInBulk(@RequestBody List<ProductRequest> productRequests){
        return productService.addProductsInBulk(productRequests);
    }
}
