package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    Optional<Plan> findByPlanTag(String planTag);
    List<Plan> findAllByAppUser(AppUser user);
}