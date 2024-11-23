package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    private String id;  // MongoDB _id

    private List<FoodItem> foodItems;  // List of food items in the order
    private Double totalAmount;
    private String restaurantName;
    private String customerName;
    private String customerPhoneNo;
    private String deliveryAddress;
    private String paymentMethod;
    private String orderStatus;

    // Nested class for FoodItem
    @Data
    public static class FoodItem {
        private String foodId;
        private String foodName;
        private Integer quantity;  // Quantity of the food item
    }
}