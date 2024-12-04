package com.ty.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ty.dto.PresentationInputDto;
import com.ty.entity.Presentation;
import com.ty.enums.PresentationStatus;
import com.ty.service.PresentationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/presentation")
public class PresentationController {

	
	@Autowired
	private PresentationService ps;
	@PostMapping("/assign")
	public ResponseEntity<?> assign(@RequestParam Integer i,@RequestParam Integer s,@RequestBody PresentationInputDto p) {
		return ps.assign(i, s, p);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> get(@RequestParam Integer i) {
		return ps.get(i);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<?> getall(@RequestParam Integer i) {
		return ps.getAll(i);		
	}
	
	@PatchMapping("/status")
	public ResponseEntity<?> status(@RequestParam Integer s,@RequestParam Integer p,@RequestParam PresentationStatus pst) {		
		return ps.updateStatus(s, p,pst);
	}
	
	@PatchMapping("/score")
	public ResponseEntity<?> score(@RequestParam Integer a,@RequestParam Integer p,@RequestParam Double d) {		
		return ps.score(a, p, d);
	}
	
	
	
}
