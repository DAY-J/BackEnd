package com.capstone.dayj.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    Optional<Plan> findByAppUserIdAndId(Integer appUserId, Integer planId);
    
    List<Plan> findAllByAppUserId(Integer appUserId);

    @Query("SELECT p FROM Plan p WHERE p.appUser.id = :userId AND p.planTag = :tag AND p.createdAt = :date")
    List<Plan> findAllByAppUserIdAndTagAndDate(@Param("userId") int userId, @Param("tag") String tag,
                                                      @Param("date") LocalDate date);
}