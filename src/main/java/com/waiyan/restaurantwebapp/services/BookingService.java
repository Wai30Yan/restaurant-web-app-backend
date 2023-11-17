package com.waiyan.restaurantwebapp.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waiyan.restaurantwebapp.model.Booking;
import com.waiyan.restaurantwebapp.repository.BookingRepository;

@Service
public class BookingService {
    // @Autowired private MongoTemplate mongoTemplate;
    @Autowired private BookingRepository bookingRepo;

    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    public Booking createBooking(Booking booking) {       
        return bookingRepo.insert(booking);
    }
    
}
