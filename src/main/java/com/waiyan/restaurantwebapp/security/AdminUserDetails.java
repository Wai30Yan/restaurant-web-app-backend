package com.waiyan.restaurantwebapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.waiyan.restaurantwebapp.model.AdminUserInfo;

import java.util.Collection;
import java.util.Collections;

public class AdminUserDetails implements UserDetails {

    private final AdminUserInfo adminUserInfo;

    public AdminUserDetails(AdminUserInfo adminUserInfo) {
        this.adminUserInfo = adminUserInfo;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can customize this method based on the roles/authorities of your admin users
        // For simplicity, assuming all admins have the role "ROLE_ADMIN"
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return adminUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return adminUserInfo.getUserName();
    }

    // Other UserDetails methods...

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

