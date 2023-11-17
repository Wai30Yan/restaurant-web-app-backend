package com.waiyan.restaurantwebapp.services.auth;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.waiyan.restaurantwebapp.model.AdminUserInfo;
import com.waiyan.restaurantwebapp.repository.AdminUserRepository;
import com.waiyan.restaurantwebapp.security.AdminUserDetails;

import java.util.Optional; 
  
@Service
public class AdminDetailsService implements UserDetailsService { 

    @Autowired private AdminUserRepository repository; 
  
    private PasswordEncoder encoder; 
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
  
        Optional<AdminUserInfo> userDetail = repository.findByUserName(username); 
  
        // Converting userDetail to UserDetails 
        return userDetail.map(AdminUserDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
  
    public String addUser(AdminUserInfo userInfo) { 
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo); 
        return "User Added Successfully"; 
    } 
  
  
} 