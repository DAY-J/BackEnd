package com.capstone.dayj.plan;

import com.capstone.dayj.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    Optional<Plan> findByAppUserIdAndId(Integer appUserId, Integer planId);
    List<Plan> findAllByAppUserIdAndPlanTag(Integer appUserId, Tag tag);
    List<Plan> findAllByAppUserId(Integer appUserId);
}