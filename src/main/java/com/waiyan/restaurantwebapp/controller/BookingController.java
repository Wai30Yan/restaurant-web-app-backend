package com.waiyan.restaurantwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.waiyan.restaurantwebapp.model.Booking;
import com.waiyan.restaurantwebapp.services.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "https://restaurant-web-app-frontend-jls78ufaz-wai30yan.vercel.app/")
public class BookingController {
    @Autowired BookingService bookingService;
    

    @PostMapping("/create-booking")
    public Booking createBooking(@RequestBody Booking booking) {
        try {
            return bookingService.createBooking(booking);
        } catch (NumberFormatException e) {
            // Handle number format exception
            throw new IllegalArgumentException("Error parsing number.", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Something went wrong.", e);
        }

    }
    
}
