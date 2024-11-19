package com.homework.rewardpoints.reward.repository;

import com.homework.rewardpoints.reward.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("Select p.price from Product p where p.productId IN :productIds")
    List<Integer> getAllPricesByProductIds(@Param("productIds") List<Long> productIds );
}
