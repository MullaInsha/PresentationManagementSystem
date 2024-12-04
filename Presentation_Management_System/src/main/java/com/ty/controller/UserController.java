package com.ty.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ty.dto.EmailDto;
import com.ty.dto.RequestLogin;
import com.ty.dto.UserInputDto;
import com.ty.entity.User;
import com.ty.enums.Status;
import com.ty.service.EmailServiceImp;
import com.ty.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
@RestController
public class UserController {

	
	@Autowired
	private UserService us;
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserInputDto user) {
		return us.register(user);
		
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?>login(@RequestBody RequestLogin rl) {
		return us.login(rl);
		
		
	}	
	@GetMapping("/get")
	public ResponseEntity<?>get(@RequestParam Integer i) {
		return us.get(i);
		
		
	}	
	@GetMapping("/fetchall")
	public ResponseEntity<?>fetchall(@RequestParam Integer a) {
		return us.FetchAll(a);
		
		
	}
	@PostMapping("/updateStatus")
	public ResponseEntity<?>updateStatus(@RequestParam Integer i,@RequestParam Integer s,@RequestParam Status st) {
		return us.updateStatus(i,s, st);
		
		
	}
	
	
}
