package com.capstone.dayj.jwt.repository;

import com.capstone.dayj.jwt.entity.RefreshEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    Boolean existsByRefresh(String refresh);
    
    @Transactional
    void deleteByRefresh(String refresh);
}
