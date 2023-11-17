package com.waiyan.restaurantwebapp.model;

public class Customer {
    public String firstName;
    public String lastName;
    public String phoneNumber;

    @Override
    public String toString() {
      return String.format(
          "Customer[firstName='%s', lastName='%s', phoneNumber='%s']",
          firstName, lastName, phoneNumber);
    }

}
