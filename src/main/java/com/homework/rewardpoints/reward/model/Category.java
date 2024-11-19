package com.homework.rewardpoints.reward.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.homework.rewardpoints.reward.dto.response.CategoryResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(unique = true, nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products;

    public CategoryResponse toCategoryResponse(){
        return CategoryResponse.builder()
                .categoryName(this.getCategoryName())
                .build();
    }
}
