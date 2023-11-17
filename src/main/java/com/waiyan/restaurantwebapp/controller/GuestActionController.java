package com.waiyan.restaurantwebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.waiyan.restaurantwebapp.model.Booking;
import com.waiyan.restaurantwebapp.model.MenuItem;
import com.waiyan.restaurantwebapp.services.BookingService;
import com.waiyan.restaurantwebapp.services.MenuItemService;


@RestController
@CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "https://restaurant-web-app-frontend-jls78ufaz-wai30yan.vercel.app/")
@RequestMapping("/guest")
public class GuestActionController {
    @Autowired private MenuItemService menuItemService;
    @Autowired private BookingService bookingService;

    @GetMapping("/get-image/{id}")
    public ResponseEntity<byte[]> findImageById(@PathVariable String id) {
        GridFSFile gridFSFile = menuItemService.findImageById(id);
        byte[] imageData = menuItemService.getImageData(gridFSFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type for your image type
    
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @GetMapping("/menu-items")
    public List<MenuItem> getAllItems() {
        return menuItemService.findAllMenuItems();
    }

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
