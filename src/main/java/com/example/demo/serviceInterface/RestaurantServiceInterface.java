package com.example.demo.serviceInterface;

import com.example.demo.model.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RestaurantServiceInterface {

    List<Restaurant> getAllRestaurants();

    Restaurant addNewRestaurant(Restaurant restaurant);
//
//    String verify(Restaurant restaurant);

   Restaurant getRestaurantById(String id);

    Restaurant updateRestaurant(String id, Restaurant restaurantDetails);

    Restaurant deleteRestaurantById(String id);

    List<Restaurant.Menu> getAllMenus(String id);


    Restaurant updateMenu(String identifier, Restaurant.Menu updatedMenu);

    Restaurant deleteMenu(String identifier, String menuId);

    String updateOrderStatus(String orderId, String statusPayload) throws JsonProcessingException;

}
