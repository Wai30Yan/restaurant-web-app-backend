package com.waiyan.restaurantwebapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.waiyan.restaurantwebapp.model.MenuItem;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    @Query("{ 'name': ?0 }")
    public MenuItem findByName(String name);

    @Query("{ 'category': { $in: ?0 } }")
    public List<MenuItem> findByCategory(List<String> category);

    @Query("{ '_id': ?0, { $set: { 'name': ?1, 'category': ?2, 'price': ?3 } } }")
    public MenuItem updateItemById(String id, String name, String category, Double price);
    

}
