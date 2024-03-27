package com.example.diningreviewapp.controller;

import com.example.diningreviewapp.model.Review;
import com.example.diningreviewapp.DTO.ReviewDto;
import com.example.diningreviewapp.model.User;
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
    private final UserRepository userRepositoryRepository;
    public ReviewController(final ReviewRepository reviewRepository, UserRepository userRepositoryRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepositoryRepository = userRepositoryRepository;
    }

    @GetMapping()
    public Iterable<ReviewDto> getAllReviews() {
        List<ReviewDto> reviewDtos = new ArrayList<ReviewDto>();
        for (Review r: this.reviewRepository.findAll()){
            reviewDtos.add(Review.EntityToDto(r));
        }
        return reviewDtos;
    }
    @PostMapping()
    public ReviewDto createNewReview(@RequestBody ReviewDto reviewDto) {
        Review review = new Review();
        review.setCommentary(reviewDto.getCommentary());
        review.setPeanut_score(reviewDto.getPeanut_score());
        review.setEgg_score(reviewDto.getEgg_score());
        review.setDairy_score(reviewDto.getDairy_score());

        Integer user_id = reviewDto.getUser_id() != null ? reviewDto.getUser_id().intValue() : null;
        Optional<User> optionalUser = this.userRepositoryRepository.findById(user_id);
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        review.setUser(user);

        this.reviewRepository.save(review);

        return Review.EntityToDto(review);
    }
    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable("id") Integer id, @RequestBody ReviewDto r) {
        Optional<Review> reviewToUpdateOptional = this.reviewRepository.findById(id);
        if (reviewToUpdateOptional.isEmpty()) return null;

        // Since isPresent() was true, we can .get() the Review object out of the Optional <<<<------
        Review reviewToUpdate = reviewToUpdateOptional.get();

        if (r.getPeanut_score() != null)  reviewToUpdate.setPeanut_score(r.getPeanut_score());
        if (r.getEgg_score() != null)  reviewToUpdate.setEgg_score(r.getEgg_score());
        if (r.getDairy_score() != null)  reviewToUpdate.setDairy_score(r.getDairy_score());
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
}
