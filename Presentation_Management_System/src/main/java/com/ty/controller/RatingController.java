package com.ty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ty.dto.EmailDto;
import com.ty.dto.RatingDto;

import com.ty.service.EmailServiceImp;
import com.ty.service.RatingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RatingController {

	
	@Autowired
	private RatingService rs;
	
	@Autowired
	private EmailServiceImp es;
	
	@PostMapping("/rate")
	public ResponseEntity<?> rate(@RequestParam Integer a,@RequestParam Integer s,@RequestParam Integer p,@RequestBody RatingDto rdto) {
			
		return rs.rate(a, s, p, rdto);
	}
	@GetMapping("/get")
	public ResponseEntity<?> rate(@RequestParam Integer a) {
		return rs.get(a);
	}
	@GetMapping("/getall")
	public ResponseEntity<?> rateall(@RequestParam Integer a) {
		return rs.getAll(a);
	}
	
	
	
}
