package com.example.diningreviewapp.DTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class RestaurantDto {
    private Long id;
    private String name;
    private List<String> review_commentaries = new ArrayList<>();
    private Double service_score = .0;
    private Double price_score = .0;
    private Double food_score = .0;
    private Double average_score = .0;
}
