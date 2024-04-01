package com.example.diningreviewapp.model;
import com.example.diningreviewapp.DTO.ReviewDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    private Double food_score = null;
    private Double service_score = null;
    private Double price_score = null;
    private String commentary = null;

    public static ReviewDto EntityToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setFood_score(review.getFood_score());
        reviewDto.setService_score(review.getService_score());
        reviewDto.setPrice_score(review.getPrice_score());
        reviewDto.setCommentary(review.getCommentary());
        reviewDto.setUser_id(review.user.getId());
        reviewDto.setUser_name(review.user.getName());
        reviewDto.setRestaurant_id(review.restaurant.getId());
        return reviewDto;
    }

}
