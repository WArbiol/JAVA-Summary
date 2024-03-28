package com.example.diningreviewapp.controller;

import com.example.diningreviewapp.model.Restaurant;
import com.example.diningreviewapp.model.Review;
import com.example.diningreviewapp.DTO.ReviewDto;
import com.example.diningreviewapp.model.User;
import com.example.diningreviewapp.repository.RestaurantRepository;
import com.example.diningreviewapp.repository.ReviewRepository;
import com.example.diningreviewapp.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/review")
@RestController
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    public ReviewController(final ReviewRepository reviewRepository, final UserRepository userRepository, final RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping()
    public Iterable<ReviewDto> getAllReviews() {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review r: this.reviewRepository.findAll()){
            reviewDtos.add(Review.EntityToDto(r));
        }
        return reviewDtos;
    }
    @PostMapping()
    public ReviewDto createNewReview(@RequestBody ReviewDto reviewDto) {
        return this.saveReviewFromDto(reviewDto);
    }
    @PostMapping("/reviews")
    public Iterable<ReviewDto> createNewReview(@RequestBody List<ReviewDto> reviewDtos) {
        List<ReviewDto> reviewDtosUpdated = new ArrayList<>();
        for (ReviewDto r: reviewDtos){
            reviewDtosUpdated.add(this.saveReviewFromDto(r));
        }
        return reviewDtosUpdated;
    }
    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("id") Integer id, @RequestBody ReviewDto r) {
        Optional<Review> reviewToUpdateOptional = this.reviewRepository.findById(id);
        if (reviewToUpdateOptional.isEmpty()) return null;

        // Since isPresent() was true, we can .get() the Review object out of the Optional <<<<------
        Review reviewToUpdate = reviewToUpdateOptional.get();

        if (r.getFood_score() != null)  reviewToUpdate.setFood_score(r.getFood_score());
        if (r.getService_score() != null)  reviewToUpdate.setService_score(r.getService_score());
        if (r.getPrice_score() != null)  reviewToUpdate.setPrice_score(r.getPrice_score());
        if (r.getCommentary() != null)  reviewToUpdate.setCommentary(r.getCommentary());
        this.reviewRepository.save(reviewToUpdate);
        return Review.EntityToDto(reviewToUpdate);
    }

    @DeleteMapping("/{id}")
    public ReviewDto deleteReview(@PathVariable("id") Integer id) {
        Optional<Review> reviewToDeleteOptional = this.reviewRepository.findById(id);
        if (reviewToDeleteOptional.isEmpty()) return null;

        Review reviewToDelete = reviewToDeleteOptional.get();
        ReviewDto reviewDto = Review.EntityToDto(reviewToDelete);
        this.reviewRepository.delete(reviewToDelete);
        return reviewDto;
    }

    private ReviewDto saveReviewFromDto(ReviewDto reviewDto) {
        Review review = new Review();
        review.setCommentary(reviewDto.getCommentary());
        review.setFood_score(reviewDto.getFood_score());
        review.setService_score(reviewDto.getService_score());
        review.setPrice_score(reviewDto.getPrice_score());

        Integer user_id = reviewDto.getUser_id() != null ? reviewDto.getUser_id().intValue() : null;
        Optional<User> optionalUser = this.userRepository.findById(user_id);
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        review.setUser(user);

        Integer restaurant_id = reviewDto.getRestaurant_id() != null ? reviewDto.getRestaurant_id().intValue() : null;
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(restaurant_id);
        if (optionalRestaurant.isEmpty()) return null;
        Restaurant restaurant = optionalRestaurant.get();
        review.setRestaurant(restaurant);
        restaurant.addReview(review);

        this.reviewRepository.save(review);
        return Review.EntityToDto(review);
    }
}
