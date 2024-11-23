package com.example.demo.service;


import com.example.demo.dao.Orders_db_repo;
import com.example.demo.dao.RestaurantDB;
import com.example.demo.model.Orders;
import com.example.demo.model.Restaurant;
//import com.example.demo.service.login.JwtTokenService;
import com.example.demo.serviceInterface.RestaurantServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService implements RestaurantServiceInterface {
    //    @Autowired
//    JwtTokenService jwtTokenService;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
    @Autowired
    Orders_db_repo ordersRepository;

    @Autowired
    RestaurantDB restaurantDB;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantDB.findAll();
    }

    @Override
    public Restaurant addNewRestaurant(Restaurant restaurant) {
        restaurant.setPassword(encoder.encode(restaurant.getPassword()));

        return restaurantDB.save(restaurant);
    }

//    @Override
//    public String verify(Restaurant restaurant) {
//        Authentication authentication= authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken
//                        (restaurant.getUsername(),restaurant.getPassword()));
//
//        if(authentication.isAuthenticated()) return jwtTokenService.generateJwtToken(restaurant.getUsername());
//        return "failed";
//    }

    @Override
    public Restaurant getRestaurantById(String identifier) {

        Restaurant existingRestaurant = null;

        // Check if the identifier is an ObjectId or restaurant name
        if (identifier.matches("[0-9a-fA-F]{24}")) {
            existingRestaurant = restaurantDB.findById(identifier).orElse(null);
        } else {
            existingRestaurant = restaurantDB.findByName(identifier);
        }

        if (existingRestaurant == null) {
            return null; // Restaurant not found
        }
        return existingRestaurant;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = null;

        // Check if the identifier is an ObjectId or restaurant name
        if (id.matches("[0-9a-fA-F]{24}")) {
            existingRestaurant = restaurantDB.findById(id).orElse(null);
        } else {
            existingRestaurant = restaurantDB.findByName(id);
        }

        if (existingRestaurant == null) {
            return null; // Restaurant not found
        }

        // Update restaurant details
        if (updatedRestaurant.getName() != null) {
            existingRestaurant.setName(updatedRestaurant.getName());
        }
        if (updatedRestaurant.getStreet() != null) {
            existingRestaurant.setStreet(updatedRestaurant.getStreet());
        }
        if (updatedRestaurant.getCity() != null) {
            existingRestaurant.setCity(updatedRestaurant.getCity());
        }
        if (updatedRestaurant.getState() != null) {
            existingRestaurant.setState(updatedRestaurant.getState());
        }
        if (updatedRestaurant.getZip() != null) {
            existingRestaurant.setZip(updatedRestaurant.getZip());
        }

        // Update menus and menu items
        if (updatedRestaurant.getMenus() != null && !updatedRestaurant.getMenus().isEmpty()) {
            for (Restaurant.Menu updatedMenu : updatedRestaurant.getMenus()) {
                // Find the existing menu by ID
                Restaurant.Menu existingMenu = existingRestaurant.getMenus().stream().filter(menu -> menu.getId().equals(updatedMenu.getId())).findFirst().orElse(null);

                if (existingMenu != null) {
                    // Update menu items if they exist in the updated request
                    if (updatedMenu.getMenuItems() != null && !updatedMenu.getMenuItems().isEmpty()) {
                        for (Restaurant.Menu.MenuItem updatedMenuItem : updatedMenu.getMenuItems()) {
                            // Find existing menu item by ID (or name, if you don't have IDs for items)
                            Restaurant.Menu.MenuItem existingMenuItem = existingMenu.getMenuItems().stream().filter(item -> item.getName().equals(updatedMenuItem.getName())).findFirst().orElse(null);

                            if (existingMenuItem != null) {
                                existingMenuItem.setPrice(updatedMenuItem.getPrice());
                                existingMenuItem.setCategory(updatedMenuItem.getCategory());
                                existingMenuItem.setIngredients(updatedMenuItem.getIngredients());
                            }
                        }
                    }
                } else {
                    // If the menu doesn't exist, you can decide to add it
                    existingRestaurant.getMenus().add(updatedMenu);
                }
            }
        }

        // Save the updated restaurant back to the repository
        Restaurant updated_restaurant = restaurantDB.save(existingRestaurant);
        return updated_restaurant;
    }


    @Override
    public Restaurant deleteRestaurantById(String identifier) {
        Restaurant existingRestaurant = null;
        if (identifier.matches("[0-9a-fA-F]{24}")) {
            existingRestaurant = restaurantDB.findById(identifier).orElse(null);
        } else {
            existingRestaurant = restaurantDB.findByName(identifier);
        }

        if (existingRestaurant == null) {
            return existingRestaurant;
        }

        restaurantDB.delete(existingRestaurant);
        return existingRestaurant;
    }

    @Override
    public List<Restaurant.Menu> getAllMenus(String identifier) {
        Restaurant existingRestaurant = null;
        if (identifier.matches("[0-9a-fA-F]{24}")) {
            existingRestaurant = restaurantDB.findById(identifier).orElse(null);
        } else {
            existingRestaurant = restaurantDB.findByName(identifier);
        }

        if (existingRestaurant == null) {
            return null; // Restaurant not found
        }

        return new ArrayList<>(existingRestaurant.getMenus());
    }

    @Override
    public Restaurant updateMenu(String identifier, Restaurant.Menu updatedMenu) {
        Restaurant existingRestaurant = null;

        // Check if the identifier is an ObjectId or restaurant name
        if (identifier.matches("[0-9a-fA-F]{24}")) { // MongoDB ObjectId check
            existingRestaurant = restaurantDB.findById(identifier).orElse(null);
        } else {
            existingRestaurant = restaurantDB.findByName(identifier);
        }

        if (existingRestaurant == null) {
            return null; // Restaurant not found
        }
        System.out.println(existingRestaurant);
        System.out.println(updatedMenu);
        System.out.println(updatedMenu.getMenuItems());
        // Find the menu by ID within the restaurant's menus
        Restaurant.Menu existingMenu = existingRestaurant.getMenus().stream().filter(menu -> menu.getId().equals(updatedMenu.getId())).findFirst().orElse(null);

        if (existingMenu != null) {
            // Update the menu items
            existingMenu.setMenuItems(updatedMenu.getMenuItems());
        } else {
            // If the menu doesn't exist, add the new menu to the restaurant
            existingRestaurant.getMenus().add(updatedMenu);
        }

        // Save the updated restaurant back to the repository
        return restaurantDB.save(existingRestaurant);
    }

    @Override
    public Restaurant deleteMenu(String identifier, String menuId) {
        Restaurant existingRestaurant = null;

        // Check if the identifier is an ObjectId or restaurant name
        if (identifier.matches("[0-9a-fA-F]{24}")) { // MongoDB ObjectId check
            existingRestaurant = restaurantDB.findById(identifier).orElse(null);
        } else {
            existingRestaurant = restaurantDB.findByName(identifier);
        }

        if (existingRestaurant == null) {
            return null; // Restaurant not found
        }

        // Find the menu to delete by its ID
        Restaurant.Menu existingMenu = existingRestaurant.getMenus().stream().filter(menu -> menu.getId().equals(menuId)).findFirst().orElse(null);

        if (existingMenu != null) {
            // Remove the menu
            existingRestaurant.getMenus().remove(existingMenu);
        } else {
            return null; // Menu not found
        }

        // Save the updated restaurant back to the repository
        return restaurantDB.save(existingRestaurant);
    }

    @Override
    public String updateOrderStatus(String orderId, String statusPayload) throws JsonProcessingException {
        List<String> ALLOWED_STATUSES = Arrays.asList("Preparation Done", "Pending", "Waiting for Delivery Partner", "Delivery Partner Arrived", "Delivery Partner on Transit", "Delivered");
//        String status = statusPayload.trim().replace("\"", ""); // Clean the raw payload (if JSON-like string is passed)
        String status = new ObjectMapper().readTree(statusPayload).get("status").asText();

        if (!ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status provided");
        }

        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(status);
        ordersRepository.save(order);

        return "Order status updated to: " + status;
    }
}
