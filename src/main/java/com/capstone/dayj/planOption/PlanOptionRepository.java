package com.capstone.dayj.planOption;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanOptionRepository extends JpaRepository<Plan, Integer> {
    List<Plan> findAllByAppUser(AppUser findAppUser);
}