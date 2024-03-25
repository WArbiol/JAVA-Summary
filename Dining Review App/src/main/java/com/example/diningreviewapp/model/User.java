package com.example.diningreviewapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue
    private Long id;

//    @Column(unique=true)
    private String name;
    private String city;
    private String state;
    private String zipcode;
    private Boolean peanut_allergies = false;
    private Boolean egg_allergies = false;
    private Boolean dairy_allergies = false;
}
