package com.waiyan.restaurantwebapp.services.auth;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.waiyan.restaurantwebapp.dto.BookingDto;
import com.waiyan.restaurantwebapp.model.Booking;
import com.waiyan.restaurantwebapp.model.MenuItem;
import com.waiyan.restaurantwebapp.model.Order;
import com.waiyan.restaurantwebapp.repository.BookingRepository;
import com.waiyan.restaurantwebapp.repository.MenuItemRepository;

@Service
public class AdminService {
    @Autowired private BookingRepository bookingRepo;
    @Autowired private MenuItemRepository menuItemRepo;
    @Autowired private MongoTemplate mongoTemplate;
    @Autowired private GridFsTemplate gridFsTemplate;

    public MenuItem createMenuItem(MenuItem item) {
        return menuItemRepo.insert(item);
    }
  
    public void updateMenuItem() {}

    public String deleteMenuItem(String id, String photoId) {
        System.out.println(id + " " + photoId);
        try {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(photoId)));
            updateRelatedBookings(id);
            menuItemRepo.deleteById(id);
            return "Item deleted successfully.";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting item";
        }
    }

    public void createBooking() {}

    public List<BookingDto> getBookings() {
        List<Booking> bookings = bookingRepo.findAll();
        List<BookingDto> result = new ArrayList<>();

        for (Booking booking: bookings) {
            List<Order> orders = booking.orders;
            List<MenuItem> menuList = new ArrayList<>();

            for (Order o: orders) {
                MenuItem item = mongoTemplate.findById(o.itemId, MenuItem.class);
                menuList.add(item);
            }
            
            BookingDto dto = new BookingDto(booking, menuList);
            result.add(dto);
        }
        return result;
    }

    public void deleteBookings() {}

    
    private void updateRelatedBookings(String menuItemId) {
        List<Booking> bookings = bookingRepo.findByOrdersItemId(menuItemId);
        for (Booking booking : bookings) {
            // Remove the order with the matching menuItemId
            booking.orders.removeIf(order -> menuItemId.equals(order.getItemId()));
            // Recalculate the total price based on the updated orders
            booking.totalPrice = calculateTotalPrice(booking.orders);
            // Save the updated booking
            bookingRepo.save(booking);
        }
    }

    private double calculateTotalPrice(List<Order> orders) {
        return orders.stream()
                .mapToDouble(order -> {
                    MenuItem menuItem = menuItemRepo.findById(order.getItemId()).orElse(null);
                    return menuItem != null ? menuItem.price * order.getCount() : 0;
                })
                .sum();
    }
    
    public String storeImage(InputStream inputStream, String fileName) {
        ObjectId photoId = gridFsTemplate.store(inputStream, fileName);
        return photoId.toString();
    }
    
    
    public void authenticate() {}



}
