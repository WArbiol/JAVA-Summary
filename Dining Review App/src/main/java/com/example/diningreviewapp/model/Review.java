package com.example.diningreviewapp.model;
import com.example.diningreviewapp.DTO.ReviewDto;
import com.example.diningreviewapp.DTO.UserDto;
import com.example.diningreviewapp.repository.UserRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Double peanut_score = null;

    private Double egg_score = null;

    private Double dairy_score = null;

    private String commentary = null;

    public static ReviewDto EntityToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setPeanut_score(review.getPeanut_score());
        reviewDto.setEgg_score(review.getEgg_score());
        reviewDto.setDairy_score(review.getDairy_score());
        reviewDto.setCommentary(review.getCommentary());
        reviewDto.setUser_id(review.user.getId());
        return reviewDto;
    }

}
