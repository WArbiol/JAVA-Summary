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
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private Double egg_score = null;
    private Double dairy_score = null;
    private Double peanut_score = null;
}
