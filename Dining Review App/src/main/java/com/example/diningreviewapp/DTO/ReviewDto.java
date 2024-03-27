package com.example.diningreviewapp.DTO;
import lombok.*;


@Data
public class ReviewDto {
    private Long id;
    private Long user_id;
    private Long restaurant_id;
    private Double peanut_score = null;
    private Double egg_score = null;
    private Double dairy_score = null;
    private String commentary = null;
}
