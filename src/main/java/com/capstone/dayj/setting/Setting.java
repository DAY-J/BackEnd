package com.capstone.dayj.setting;


import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"appUser"})
public class Setting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String profilePhoto;
    
    @Column(nullable = false)
    private List<AlarmSetting> alarmSettings;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    @Transactional
    @PrePersist
    public void prePersist() {
        this.alarmSettings = List.of(AlarmSetting.ALL, AlarmSetting.PLAN,
                AlarmSetting.FRIEND_GROUP, AlarmSetting.POST, AlarmSetting.APP);
    }
    
    public void update(List<AlarmSetting> alarmSettings, String profilePhoto) {
        this.alarmSettings = alarmSettings;
        this.profilePhoto = profilePhoto;
    }
    
    @Builder
    public Setting(int id, List<AlarmSetting> alarmSettings, String profilePhoto, AppUser appUser) {
        this.id = id;
        this.alarmSettings = alarmSettings;
        this.profilePhoto = profilePhoto;
        this.appUser = appUser;
    }
}
