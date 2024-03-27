package com.example.diningreviewapp.DTO;
import lombok.Data;

import com.example.diningreviewapp.model.Review;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserDto {
    private Long id;
    private List<Long> review_ids = new ArrayList<Long>();
    private String name;
    private String city;
    private String state;
    private String zipcode;
    private Boolean peanut_allergies;
    private Boolean egg_allergies;
    private Boolean dairy_allergies;

}
