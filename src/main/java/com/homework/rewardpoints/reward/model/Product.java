package com.homework.rewardpoints.reward.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.homework.rewardpoints.reward.dto.response.ProductResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Double price;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Purchase> purchases;

    public ProductResponse toProductResponse(){
        return ProductResponse.builder()
                .productName(this.getProductName())
                .price(this.getPrice())
                .category(this.category.getCategoryName())
                .build();
    }
}
