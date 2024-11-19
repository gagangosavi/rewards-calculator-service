package com.homework.rewardpoints.reward.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.homework.rewardpoints.reward.dto.response.PurchaseResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    private LocalDateTime purchaseTime;

    private Double purchaseAmount = 0.0;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_purchase",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ToString.Exclude
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id")
    @ToString.Exclude
    private RewardPoints rewardPoints;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Customer customer;

    public PurchaseResponse toPurchaseResponse(){
        return PurchaseResponse.builder()
                .customerResponse(this.customer.toCustomerResponse())
                .purchaseTime(this.getPurchaseTime())
                .purchaseAmount(this.getPurchaseAmount())
                .totalRewardPoints(this.getRewardPoints().getRewardsEarned())
                .productResponses(this.products.stream().map(Product::toProductResponse).collect(Collectors.toList()))
                .build();
    }
}
