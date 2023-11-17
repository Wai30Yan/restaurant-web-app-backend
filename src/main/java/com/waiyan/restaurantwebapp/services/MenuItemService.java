package com.waiyan.restaurantwebapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.waiyan.restaurantwebapp.model.MenuItem;
import com.waiyan.restaurantwebapp.repository.MenuItemRepository;

@Service
public class MenuItemService {
    
    @Autowired private MenuItemRepository menuItemRepo;
    @Autowired private MongoTemplate mongoTemplate;
    @Autowired private GridFsTemplate gridFsTemplate;

    public List<MenuItem> findAllMenuItems() {
        return menuItemRepo.findAll();
    }

    public MenuItem findItemById(String id) {
        return mongoTemplate.findById(id, MenuItem.class);
    }

    public List<MenuItem> findItemByCategory(List<String> category) {
        return menuItemRepo.findByCategory(category);
    }


    public MenuItem updateItemById(String id, Map<String, String> item) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();

        for (Map.Entry<String, String> i: item.entrySet()) {
            if (i.getKey() == "price") {
                update = new Update().set(i.getKey(), Double.parseDouble(i.getValue()));
            } else {
                update = new Update().set(i.getKey(), i.getValue());
            }
        }

        return mongoTemplate.findAndModify(query, update, MenuItem.class);

    }



    public GridFSFile findImageById(String id) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    }

    public GridFsResource findImageResource(String id) {
        GridFSFile file = findImageById(id);
        return new GridFsResource(file);
    }

    public byte[] getImageData(GridFSFile gridFSFile) {
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        try (InputStream inputStream = resource.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle exception
        }

        return null;
    }

    public void deletePhotos(String id) {
        menuItemRepo.deleteById(id);
    }
    

}
