package com.homework.rewardpoints.reward.repository;

import com.homework.rewardpoints.reward.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
