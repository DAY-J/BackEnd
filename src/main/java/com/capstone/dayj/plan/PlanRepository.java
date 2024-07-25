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

    // 통계확인 하고자하는 시작날짜와 끝날짜 사이 -> 달력으로 하면 수정 필요
    @Query("SELECT p FROM Plan p WHERE p.appUser.id = :userId AND p.planTag = :tag AND p.createdAt BETWEEN :startDate AND :endDate")
    List<Plan> findAllByAppUserIdAndTagAndDateBetween(@Param("userId") int userId, @Param("tag") String tag, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}