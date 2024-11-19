package com.homework.rewardpoints.reward.repository;

import com.homework.rewardpoints.reward.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
