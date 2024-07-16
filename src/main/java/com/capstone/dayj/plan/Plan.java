package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String planTag;
    @Column(nullable = false)
    private String goal;
    private String planPhoto;
    
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isComplete;
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isPublic;
    
    private LocalDateTime planAlarmTime;
    @Column(nullable = false)
    private LocalDateTime planStartTime;
    @Column(nullable = false)
    private LocalDateTime planEndTime;
    private LocalDateTime planRepeatStartDate;
    private LocalDateTime planRepeatEndDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    public void update(String planTag, String goal, String planPhoto, boolean isPublic, boolean isComplete, LocalDateTime planAlarmTime, LocalDateTime planStartTime, LocalDateTime planEndTime, LocalDateTime planRepeatStartDate, LocalDateTime planRepeatEndDate) {
        this.planTag = planTag;
        this.goal = goal;
        this.planPhoto = planPhoto;
        this.isPublic = isPublic;
        this.isComplete = isComplete;
        this.planAlarmTime = planAlarmTime;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.planRepeatStartDate = planRepeatStartDate;
        this.planRepeatEndDate = planRepeatEndDate;
    }
    
    @Builder
    public Plan(int id, String planTag, String goal, String planPhoto, boolean isComplete, boolean isPublic, LocalDateTime planAlarmTime, LocalDateTime planStartTime, LocalDateTime planEndTime, LocalDateTime planRepeatStartDate, LocalDateTime planRepeatEndDate, AppUser appUser) {
        this.id = id;
        this.planTag = planTag;
        this.goal = goal;
        this.planPhoto = planPhoto;
        this.isComplete = isComplete;
        this.isPublic = isPublic;
        this.planAlarmTime = planAlarmTime;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.planRepeatStartDate = planRepeatStartDate;
        this.planRepeatEndDate = planRepeatEndDate;
        this.appUser = appUser;
        
    }
}

