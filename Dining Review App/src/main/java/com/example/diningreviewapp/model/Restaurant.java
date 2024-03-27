package com.example.diningreviewapp.model;

import com.example.diningreviewapp.DTO.RestaurantDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "restaurant", fetch= FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    public static RestaurantDto EntityToDto(Restaurant restaurant){
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setId(restaurant.getId());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setEgg_score(restaurant.egg_score());
        restaurantDto.setDairy_score(restaurant.dairy_score());
        restaurantDto.setPeanut_score(restaurant.peanut_score());
        restaurantDto.setAverage_score(restaurant.average_score());

        List<Long> review_ids = new ArrayList<>();
        for (Review r: restaurant.getReviews()) review_ids.add(r.getId());

        restaurantDto.setReview_ids(review_ids);
        return restaurantDto;
    }
    public void addReview(Review r){
        reviews.add(r);
    }
    public Double egg_score(){
        double score = .0;
        int egg_score_size = 0;
        for (Review r: this.reviews) {
            if (r.getEgg_score() != null) {
                score += r.getEgg_score();
                egg_score_size += 1;
            }
        }
        score/=egg_score_size;
        return score;
    }
    public Double dairy_score(){
        double score = .0;
        int dairy_score_size = 0;
        for (Review r: this.reviews){
            if (r.getDairy_score()!=null){
                score+=r.getDairy_score();
                dairy_score_size+=1;
            }
        }
        score/=dairy_score_size;
        return score;
    }
    public Double peanut_score(){
        double score = .0;
        int peanut_score_size = 0;
        for (Review r: this.reviews){
            if (r.getPeanut_score()!=null){
                score+=r.getPeanut_score();
                peanut_score_size+=1;
            }
        }
        score/=peanut_score_size;
        return score;
    }
    public Double average_score(){
        double score = .0;
        int score_size = 0;
        for (Review r: this.reviews) {
            if (r.getEgg_score() != null) {
                score += r.getEgg_score();
                score_size += 1;
            }
            if (r.getDairy_score()!=null){
                score+=r.getDairy_score();
                score_size+=1;
            }
            if (r.getPeanut_score()!=null){
                score+=r.getPeanut_score();
                score_size+=1;
            }
        }
        score/=score_size;
        return score;
    }
}
