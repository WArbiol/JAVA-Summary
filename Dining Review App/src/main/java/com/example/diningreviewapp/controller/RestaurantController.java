package com.example.diningreviewapp.controller;

import com.example.diningreviewapp.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restaurant")
@RestController
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    public RestaurantController(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
}
