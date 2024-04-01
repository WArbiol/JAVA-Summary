package com.example.diningreviewapp.model;
import com.example.diningreviewapp.DTO.UserDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data //getter and setter methods are auto generated
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<Review>();

    private String name;
    private String city;
    private String state;
    private String zipcode;
    private Boolean peanut_allergies = false;
    private Boolean egg_allergies = false;
    private Boolean dairy_allergies = false;

    public static UserDto EntityToDto(User user){
        UserDto userDto = new UserDto();

        userDto.setCity(user.getCity());
        userDto.setState(user.getState());
        userDto.setName(user.getName());
        userDto.setZipcode(user.getZipcode());
        userDto.setPeanut_allergies(user.getPeanut_allergies());
        userDto.setEgg_allergies(user.getEgg_allergies());
        userDto.setDairy_allergies(user.getDairy_allergies());

        List<String> review_commentaries = new ArrayList<>();
        for (Review r: user.getReviews()) review_commentaries.add(r.getCommentary());
        userDto.setReview_commentaries(review_commentaries);
        return userDto;
    }
}
