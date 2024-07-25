package com.capstone.dayj.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    // 필요한 경우 사용자 정의 쿼리 정의
}
