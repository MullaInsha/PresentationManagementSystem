package com.ty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ty.enums.PresentationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ratings_app")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    private Integer communication;
    private Integer confidence;
    private Integer content;
    private Integer interaction;
    private Integer liveliness;
    private Integer usageProps;
    private Double totalScore;


    
    // Unidirectional Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Unidirectional Many-to-One relationship with Presentation
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "presentation_id", nullable = false)
    private Presentation presentation;
}
