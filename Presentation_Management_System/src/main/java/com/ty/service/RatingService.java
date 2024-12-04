package com.ty.service;

import org.springframework.http.ResponseEntity;

import com.ty.dto.EmailDto;
import com.ty.dto.RatingDto;
import com.ty.entity.Rating;

public interface RatingService {

	ResponseEntity<?> rate(Integer a,Integer s,Integer p,RatingDto rto);
	ResponseEntity<?> get(Integer p);
	ResponseEntity<?> getAll(Integer s);
	
	
}
