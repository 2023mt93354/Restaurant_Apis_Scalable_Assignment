package com.example.demo.dao;

import com.example.demo.model.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Orders_db_repo extends MongoRepository<Orders, String> {
    List<Orders> findByCustomerName(String customerName);
    List<Orders> findByRestaurantName(String restaurantName);
    List<Orders> findByOrderStatus(String orderStatus);
}