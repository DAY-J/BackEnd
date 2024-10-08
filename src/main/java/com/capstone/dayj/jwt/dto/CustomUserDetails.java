package com.capstone.dayj.jwt.dto;

import com.capstone.dayj.appUser.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final AppUser appUser;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return appUser.getRole();
            }
        });
        
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return appUser.getPassword();
    }
    
    @Override
    public String getUsername() {
        return appUser.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}