package com.homework.rewardpoints.reward.repository;

import com.homework.rewardpoints.reward.model.Purchase;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @Query("SELECT p FROM Purchase p WHERE p.customer.customerId IN :customerIds AND p.purchaseTime BETWEEN :startDate AND :endDate")
    List<Purchase> findByCustomerIdsInPurchaseTimeBetween(List<Long> customerIds, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT p FROM Purchase p WHERE FUNCTION('MONTH', p.purchaseTime) IN :months")
    List<Purchase> findAllByMonths(@Param("months") List<Integer> months);

    @Query("SELECT p FROM Purchase p WHERE p.customer.customerId IN :customerIds")
    List<Purchase> findByCustomerIdsIn(List<Long> customerIds);
}
