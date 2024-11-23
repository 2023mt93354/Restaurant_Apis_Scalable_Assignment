package com.example.demo.dao;

import com.example.demo.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDB extends MongoRepository<Restaurant,String> {
    Restaurant findByUsername(String username);
    Restaurant findByName(String name);
}
