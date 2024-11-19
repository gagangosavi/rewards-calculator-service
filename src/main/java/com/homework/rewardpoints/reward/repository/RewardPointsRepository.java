package com.homework.rewardpoints.reward.repository;

import com.homework.rewardpoints.reward.model.RewardPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardPointsRepository extends JpaRepository<RewardPoints,Long> {
}
