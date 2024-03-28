package com.example.diningreviewapp.DTO;
import lombok.*;


@Data
public class ReviewDto {
    private Long id;
    private Long user_id;
    private Long restaurant_id;
    private Double food_score = null;
    private Double service_score = null;
    private Double price_score = null;
    private String commentary = null;
}
