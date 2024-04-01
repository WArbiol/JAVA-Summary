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

        restaurantDto.setName(restaurant.getName());
        restaurantDto.setService_score(restaurant.service_score());
        restaurantDto.setPrice_score(restaurant.price_score());
        restaurantDto.setFood_score(restaurant.food_score());
        restaurantDto.setAverage_score(restaurant.average_score());

        List<String> review_commentaries = new ArrayList<>();
        for (Review r: restaurant.getReviews()) review_commentaries.add(r.getCommentary());
        restaurantDto.setReview_commentaries(review_commentaries);

        return restaurantDto;
    }
    public void addReview(Review r){
        reviews.add(r);
    }
    public Double service_score(){
        double score = .0;
        int service_score_size = 0;
        for (Review r: this.reviews) {
            if (r.getService_score() != null) {
                score += r.getService_score();
                service_score_size += 1;
            }
        }
        score/=service_score_size;
        return score;
    }
    public Double price_score(){
        double score = .0;
        int price_score_size = 0;
        for (Review r: this.reviews){
            if (r.getPrice_score()!=null){
                score+=r.getPrice_score();
                price_score_size+=1;
            }
        }
        score/=price_score_size;
        return score;
    }
    public Double food_score(){
        double score = .0;
        int food_score_size = 0;
        for (Review r: this.reviews){
            if (r.getFood_score()!=null){
                score+=r.getFood_score();
                food_score_size+=1;
            }
        }
        score/=food_score_size;
        return score;
    }
    public Double average_score(){
        double score = .0;
        int score_size = 0;
        for (Review r: this.reviews) {
            if (r.getService_score() != null) {
                score += r.getService_score();
                score_size += 1;
            }
            if (r.getPrice_score()!=null){
                score+=r.getPrice_score();
                score_size+=1;
            }
            if (r.getFood_score()!=null){
                score+=r.getFood_score();
                score_size+=1;
            }
        }
        score/=score_size;
        return score;
    }
}
