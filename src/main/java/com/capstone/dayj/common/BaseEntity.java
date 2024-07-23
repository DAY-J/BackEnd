package com.capstone.dayj.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass // 엔티티 클래스가 상속받을 경우 자식 클래스에게 매핑 정보를 전달
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    
    @CreatedDate  // 데이터 생성 날짜를 자동으로 주입
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate  // 데이터 수정 날짜를 자동으로 주입
    private LocalDateTime updatedAt;
}