package com.waiyan.restaurantwebapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


public class DatabaseClient extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
	private String connectionString;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    public MongoClient mongoClient() {;
        return MongoClients.create(MongoClientSettings.builder()
                                                      .applyConnectionString(new ConnectionString(connectionString))
                                                      .build());
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
}
