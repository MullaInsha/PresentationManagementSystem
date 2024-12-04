package com.ty.service;

import org.springframework.http.ResponseEntity;

import com.ty.dto.PresentationInputDto;
import com.ty.entity.Presentation;
import com.ty.enums.PresentationStatus;

public interface PresentationService {
	
	
ResponseEntity<?> assign(Integer a,Integer s,PresentationInputDto pdto);
ResponseEntity<?> get(Integer p);
ResponseEntity<?> getAll(Integer s);
ResponseEntity<?> updateStatus(Integer s,Integer p,PresentationStatus ps);
ResponseEntity<?> score(Integer a, Integer p, Double d);

}
