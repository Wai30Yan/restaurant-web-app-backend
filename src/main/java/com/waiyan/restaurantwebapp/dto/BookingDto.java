package com.waiyan.restaurantwebapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waiyan.restaurantwebapp.model.Booking;
import com.waiyan.restaurantwebapp.model.MenuItem;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDto {
    private Booking booking;
    private List<MenuItem> menuItems;

    public Booking getBooking() {
        return booking;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public BookingDto(Booking booking, List<MenuItem> menuItems) {
        this.booking = booking;
        this.menuItems = menuItems;
    }
    
    public BookingDto(List<MenuItem> menuItems) {
        // this.booking = booking;
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        return String.format("BookingDto[booking='%s', menuItems='%s']", booking, menuItems);
    }
}
