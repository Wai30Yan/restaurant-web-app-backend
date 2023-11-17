package com.waiyan.restaurantwebapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="menu-items")
public class MenuItem {
    @Id 
    public String id;
    public String name;
    public String category;
    public Double price;
    public String photoId;

    @Override
    public String toString() {
        return String.format("MenuItem[id=%s, name='%s', category='%s' price='%.2f', photoId='%s']", id, name, category, price, photoId);
    }

}
