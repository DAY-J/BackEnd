package com.capstone.dayj.plan;

import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.capstone.dayj.planOption.PlanOption;
import com.capstone.dayj.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private Tag planTag;
    @Column(nullable = false)
    private String goal;
    
    private String planPhoto;
    
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isComplete;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isPublic;
    
    @OneToOne(mappedBy = "plan", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private PlanOption planOption;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser appUser;
    
    public void update(PlanDto.Request dto) {
        this.planTag = (dto.getPlanTag() == null ? this.planTag : dto.getPlanTag());
        this.goal = (dto.getGoal() == null ? this.goal : dto.getGoal());
        this.planPhoto = dto.getPlanPhoto();
        this.isPublic = (dto.getGoal() == null ? this.isPublic : dto.getIsPublic());
        this.isComplete = (dto.getGoal() == null ? this.isComplete : dto.getIsComplete());
        this.planOption = (dto.getPlanOption() == null ? this.planOption : dto.getPlanOption());
    }
    
    @Builder
    public Plan(int id, Tag planTag, String goal, String planPhoto, Boolean isComplete, Boolean isPublic, PlanOption planOption, AppUser appUser) {
        this.id = id;
        this.planTag = planTag;
        this.goal = goal;
        this.planPhoto = planPhoto;
        this.isComplete = isComplete;
        this.isPublic = isPublic;
        this.planOption = planOption;
        this.appUser = appUser;
        
    }
}

