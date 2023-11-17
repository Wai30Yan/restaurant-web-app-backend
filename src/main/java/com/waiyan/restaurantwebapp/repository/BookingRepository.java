package com.waiyan.restaurantwebapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.waiyan.restaurantwebapp.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByOrdersItemId(String menuItemId);
    
}
