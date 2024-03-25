package com.example.diningreviewapp.repository;
import com.example.diningreviewapp.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
}
