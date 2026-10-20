package com.onist.commentary.configMongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

// This configuration class sets up the MongoDB client for the application. 
// It reads the MongoDB URI from the application properties and creates a MongoClient bean 
// that can be used throughout the application to interact with the MongoDB database
// Cette classe de configuration met en place le client MongoDB pour l'application. 
// Elle lit l'URI MongoDB à partir des propriétés de l'application et crée un bean `MongoClient` 
// qui peut être utilisé dans toute l'application pour interagir avec la base de données MongoDB
@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }
}