package com.capstone.dayj.setting;


import com.capstone.dayj.appUser.AppUser;
import com.capstone.dayj.common.BaseEntity;
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
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean planAlarm;
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean friendGroupAlarm;
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean postAlarm;
    
    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean appAlarm;
    
    private String profilePhoto;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    
    public void update(boolean planAlarm, boolean friendGroupAlarm, boolean postAlarm, boolean appAlarm, String profilePhoto) {
        this.planAlarm = planAlarm;
        this.friendGroupAlarm = friendGroupAlarm;
        this.postAlarm = postAlarm;
        this.appAlarm = appAlarm;
        this.profilePhoto = profilePhoto;
    }
    
    @Builder
    public Setting(int id, boolean planAlarm, boolean friendGroupAlarm, boolean postAlarm, boolean appAlarm, String profilePhoto, AppUser appUser) {
        this.id = id;
        this.planAlarm = planAlarm;
        this.friendGroupAlarm = friendGroupAlarm;
        this.postAlarm = postAlarm;
        this.appAlarm = appAlarm;
        this.profilePhoto = profilePhoto;
        this.appUser = appUser;
    }
}
