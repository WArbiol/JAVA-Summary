package com.example.diningreviewapp.DTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class RestaurantDto {
    private Long id;
    private String name;
    private List<Long> review_ids = new ArrayList<Long>();
    private Double egg_score = .0;
    private Double dairy_score = .0;
    private Double peanut_score = .0;
    private Double average_score = .0;
}
