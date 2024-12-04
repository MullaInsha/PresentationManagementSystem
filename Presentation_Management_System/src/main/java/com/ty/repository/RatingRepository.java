package com.ty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer>{

}
