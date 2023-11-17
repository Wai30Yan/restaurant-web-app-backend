package com.waiyan.restaurantwebapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waiyan.restaurantwebapp.model.AdminUserInfo;


public interface AdminUserRepository extends MongoRepository<AdminUserInfo, String> {

    Optional<AdminUserInfo> findByUserName(String username);
    
}
