package com.example.diningreviewapp.controller;

import com.example.diningreviewapp.DTO.RestaurantDto;
import com.example.diningreviewapp.DTO.ReviewDto;
import com.example.diningreviewapp.DTO.UserDto;
import com.example.diningreviewapp.model.Restaurant;
import com.example.diningreviewapp.model.User;
import com.example.diningreviewapp.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/restaurant")
@RestController
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    public RestaurantController(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping()
    public Iterable<RestaurantDto> getAllRestaurants() {
        List<RestaurantDto> restaurantDtos = new ArrayList<RestaurantDto>();
        for (Restaurant r: this.restaurantRepository.findAll()){
            restaurantDtos.add(Restaurant.EntityToDto(r));
        }
        return restaurantDtos;
    }

    @PostMapping()
    public Restaurant createNewRestaurant(@RequestBody Restaurant restaurant)  {
        return this.restaurantRepository.save(restaurant);
    }
    @PostMapping("/restaurants")
    public Iterable<Restaurant>  createNewRestaurants(@RequestBody List<Restaurant> restaurants)  {
        return this.restaurantRepository.saveAll(restaurants);
    }

    @PutMapping("/{id}")
    public RestaurantDto updateRestaurant(@PathVariable("id") Integer id, @RequestBody RestaurantDto r){
        Optional<Restaurant> restaurantToUpdateOptional = this.restaurantRepository.findById(id);
        if (restaurantToUpdateOptional.isEmpty()) return null;
        Restaurant restaurantToUpdate = restaurantToUpdateOptional.get();

        if (r.getName() != null)  restaurantToUpdate.setName(r.getName());
        return Restaurant.EntityToDto(this.restaurantRepository.save(restaurantToUpdate));
    }

    @DeleteMapping("/{id}")
    public RestaurantDto deleteRestaurant(@PathVariable("id") Integer id) {
        Optional<Restaurant> restaurantToDeleteOptional = this.restaurantRepository.findById(id);
        if (restaurantToDeleteOptional.isEmpty()) return null;

        Restaurant restaurantToDelete = restaurantToDeleteOptional.get();
        RestaurantDto restaurantdto = Restaurant.EntityToDto(restaurantToDelete);
        this.restaurantRepository.delete(restaurantToDelete);
        return restaurantdto;
    }
}
