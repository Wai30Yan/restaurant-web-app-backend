package com.waiyan.restaurantwebapp.controller;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.waiyan.restaurantwebapp.dto.BookingDto;
import com.waiyan.restaurantwebapp.model.MenuItem;
import com.waiyan.restaurantwebapp.services.MenuItemService;
import com.waiyan.restaurantwebapp.services.auth.AdminService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "https://restaurant-web-app-frontend-jls78ufaz-wai30yan.vercel.app/")
@RequestMapping("/admin")
public class AdminController {
    @Autowired private AdminService adminService;
    @Autowired private MenuItemService menuItemService;

    @PostMapping("/menu-items")
    public MenuItem createMenuItem(@RequestParam Map<String, String> formData, @RequestParam("photo") MultipartFile photo) {
        try {
            String name = formData.get("name");
            String category = formData.get("category");
            String priceString = formData.get("price");

            if (name == null || category == null || priceString == null || photo == null) {
                throw new IllegalArgumentException("One or more form fields are missing.");
            }

            Double price = Double.parseDouble(priceString);

            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative.");
            }

            InputStream inputStream = photo.getInputStream();
            String fileId = adminService.storeImage(inputStream, photo.getOriginalFilename());
            
            if (fileId == null) {
                throw new Exception("Error storing image.");
            }

            MenuItem item = new MenuItem();
            item.name = name;
            item.category = category;
            item.price = price;
            item.photoId = fileId;

            return adminService.createMenuItem(item);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Number format not accepted." + e.getMessage());
        } catch (Exception e) {
            
        }
        return null;
    }


    @GetMapping("/menu-items")
    public List<MenuItem> getAllItems(HttpServletRequest request) {
        System.out.println("/n-------Admin Menu Items Req---------\n");
        System.out.println(request.getHeader("Authorization"));
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                String headerValue = headerValues.nextElement();
                System.out.println(headerName + ": " + headerValue);
            }
        }
        return menuItemService.findAllMenuItems();
    }

    @GetMapping("/get-image/{id}")
    public ResponseEntity<byte[]> findImageById(@PathVariable String id) {
        GridFSFile gridFSFile = menuItemService.findImageById(id);
        byte[] imageData = menuItemService.getImageData(gridFSFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type for your image type
    
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PutMapping("/menu-items")
    public void updateMenuItem() {}

    @DeleteMapping("/menu-items")
    public ResponseEntity<String> deleteMenuItem(@RequestParam("id") String id,
        @RequestParam("photoId") String photoId   
    ) {
        String result = adminService.deleteMenuItem(id, photoId);
        
        if ("Item deleted successfully.".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/bookings")
    public List<BookingDto> getBookings(HttpServletRequest request) {
        return adminService.getBookings();
    }

    @DeleteMapping("/bookings")
    public ResponseEntity<String> deleteBookings() {
        return ResponseEntity.ok("The delete feature is to be added soon.");
    }

    @PutMapping("/bookings")
    public ResponseEntity<String> updateBookings() {
        return ResponseEntity.ok("The update feature is to be added soon.");
    }

}


// Enumeration<String> headerNames = request.getHeaderNames();
// while (headerNames.hasMoreElements()) {
//     String headerName = headerNames.nextElement();
//     Enumeration<String> headerValues = request.getHeaders(headerName);
//     while (headerValues.hasMoreElements()) {
//         String headerValue = headerValues.nextElement();
//         System.out.println(headerName + ": " + headerValue);
//     }
// }