package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Restaurant;
import com.example.demo.serviceInterface.RestaurantServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
@Tag(name="restaurant")
public class RestaurantController {

    @Autowired
    RestaurantServiceInterface restaurantInt;

//    @GetMapping("/get-all-restaurants")
//    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
//        return ResponseEntity.ok(restaurantInt.getAllRestaurants());
//    }
@GetMapping("/get-all-restaurants")
public ResponseEntity<List<Restaurant>> getAllRestaurants() {
    return ResponseEntity.ok(restaurantInt.getAllRestaurants());
}

//    @PostMapping("/register")
//    public ResponseEntity<Restaurant> addNewRestaurant(@RequestBody Restaurant restaurant) {
//        return new ResponseEntity<>(restaurantInt.addNewRestaurant(restaurant), HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Restaurant restaurant) {
//        return new ResponseEntity<>(restaurantInt.verify(restaurant), HttpStatus.ACCEPTED);
//    }


    @GetMapping("/{identifier}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String identifier) {
      Restaurant restaurant = restaurantInt.getRestaurantById(identifier);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant); // Menu deleted successfully
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Restaurant or Menu not found
        }
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Restaurant> deleteRestaurantById(@PathVariable String identifier) {
        try {
            Restaurant deletedRestaurant = restaurantInt.deleteRestaurantById(identifier);
            return new ResponseEntity<>(deletedRestaurant, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }




    @GetMapping("/menus/{identifier}")
    public ResponseEntity<List<Restaurant.Menu>> getAllMenus(@PathVariable String identifier) {
        List<Restaurant.Menu> menus = restaurantInt.getAllMenus(identifier);
        return ResponseEntity.ok(menus);
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String identifier, @RequestBody Restaurant restaurantDetails) {
        try {
            Restaurant updatedRestaurant = restaurantInt.updateRestaurant(identifier, restaurantDetails);
            return ResponseEntity.ok(updatedRestaurant);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("/add-menu/{identifier}")
    public ResponseEntity<Restaurant> updateMenu(@PathVariable String identifier, @RequestBody Restaurant.Menu updatedMenu) {
        Restaurant updatedRestaurant = restaurantInt.updateMenu(identifier, updatedMenu);
        if (updatedRestaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/delete-menu/{identifier}/{menuId}")
    public ResponseEntity<Restaurant> deleteMenu(@PathVariable String identifier, @PathVariable String menuId) {
        Restaurant updatedRestaurant = restaurantInt.deleteMenu(identifier, menuId);

        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant); // Menu deleted successfully
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Restaurant or Menu not found
        }
    }
    @PatchMapping("/status-update/{orderId}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody String statusPayload) throws JsonProcessingException {

        return ResponseEntity.ok(restaurantInt.updateOrderStatus(orderId, statusPayload));
    }


}
