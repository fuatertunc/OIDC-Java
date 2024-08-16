package com.mongodb.oidc;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ){
        
        String mongodbUri = "mongodb+srv://cluster0.hujtd.mongodb.net";
        
        MongoCredential credential = MongoCredential.createOidcCredential(null)
                .withMechanismProperty("ENVIRONMENT", "gcp")
                .withMechanismProperty("TOKEN_RESOURCE", "MongoDB");

        MongoClientSettings clientBuilder = MongoClientSettings.builder()
                                        .applyConnectionString(new ConnectionString(mongodbUri))
                                        .credential(credential)
                                        .build();
        try (MongoClient mongoClient = MongoClients.create(clientBuilder)) {
        /*
        try(MongoClient mongoClient = MongoClients.create(
            mongodbUri + "authMechanism=MONGODB-OIDC"+"&authMechanismProperties=ENVIRONMENT:gcp, TOKEN_RESOURCE:MongoDB");) { */  
        // Accessing a collection
                MongoDatabase db = mongoClient.getDatabase("demo_db");
                MongoCollection<Document> collection = db.getCollection("collection1");
                
                // Inserting a document
                Document document = new Document("value", "984");
                collection.insertOne(document);
                logger.info("Inserted ID: " + document.getObjectId("_id").toHexString());
                logger.info("----------------------------------");

        }
    }
}
