package com.homework.rewardpoints.reward.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.homework.rewardpoints.reward.dto.response.CustomerResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String firstName;
    private String lastName;

    private String email;

    private String contact;

    @Column(nullable = false)
    private long totalRewardPoints = 0l;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Purchase> purchases;

    public CustomerResponse toCustomerResponse(){
        return CustomerResponse.builder()
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .contact(this.getContact())
                .email(this.getEmail())
                .totalRewardPoints(this.getTotalRewardPoints())
                .build();
    }

}
