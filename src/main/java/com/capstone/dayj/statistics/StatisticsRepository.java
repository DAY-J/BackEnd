package com.capstone.dayj.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    Statistics findByAppUserIdAndDate(int app_user_id, LocalDate date);
}
