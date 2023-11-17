package com.waiyan.restaurantwebapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.waiyan.restaurantwebapp.model.MenuItem;
import com.waiyan.restaurantwebapp.services.MenuItemService;


@CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "https://restaurant-web-app-frontend-jls78ufaz-wai30yan.vercel.app/")
@RestController
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/menu-items")
    public List<MenuItem> getAllItems() {
        return menuItemService.findAllMenuItems();
    }
    
    @GetMapping("/find-by-category")
    public List<MenuItem> findItemByCategory(@RequestParam("category") List<String> category) {
        return menuItemService.findItemByCategory(category);
    }



    @GetMapping("/menu-items/{id}")
    public ResponseEntity<MenuItem> findItemById(@PathVariable String id) {
        MenuItem item = menuItemService.findItemById(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }   
    
    @GetMapping("/get-image/{id}")
    public ResponseEntity<byte[]> findImageById(@PathVariable String id) {
        GridFSFile gridFSFile = menuItemService.findImageById(id);
        byte[] imageData = menuItemService.getImageData(gridFSFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type for your image type
    
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


    @PutMapping("/menu-items/{id}")
    public MenuItem updateItemById(@PathVariable String id, @RequestBody Map<String, String> updatedItem) {
        return menuItemService.updateItemById(id, updatedItem);
    }    

}

