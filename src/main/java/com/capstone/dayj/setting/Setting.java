package com.capstone.dayj.setting;


import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("0")
    private boolean alarm;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    @JsonIgnore
    private AppUser appUser;
    
    public void update(SettingDto.Request dto) {
        this.profilePhoto = dto.getProfilePhoto();
        this.alarm = dto.isAlarm();
    }
    
    @Builder
    public Setting(int id, String profilePhoto, boolean alarm, AppUser appUser) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.alarm = alarm;
        this.appUser = appUser;
    }
}
