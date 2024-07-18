package com.capstone.dayj.planOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanOptionRepository extends JpaRepository<PlanOption, Integer> {
}