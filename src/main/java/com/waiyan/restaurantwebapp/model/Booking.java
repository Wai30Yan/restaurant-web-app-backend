package com.waiyan.restaurantwebapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="bookings")
public class Booking {
    @Id
    public String id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public Long date;
    public Double totalPrice;
    public List<Order> orders;
    
    public Booking(String firstName, String lastName, String phoneNumber, Long date, Double totalPrice,
            List<Order> orders
            ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.totalPrice = totalPrice;
        this.orders = orders;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", orders=" + orders +
                '}';
    }

}
