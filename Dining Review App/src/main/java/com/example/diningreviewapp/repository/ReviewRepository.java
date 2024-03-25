package com.example.diningreviewapp.repository;
import com.example.diningreviewapp.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
