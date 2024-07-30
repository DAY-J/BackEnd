package com.capstone.dayj.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    List<Statistics> findAllByAppUserIdAndDateBetween(int userId, LocalDate startDate, LocalDate endDate);
}
