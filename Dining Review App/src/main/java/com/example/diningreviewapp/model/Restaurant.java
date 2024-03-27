package com.example.diningreviewapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name="RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="EGG_SCORE")
    private Double egg_score = null;

    @Column(name="DAIRY_SCORE")
    private Double dairy_score = null;

    @Column(name="PEANUT_SCORE")
    private Double peanut_score = null;
}
