package com.ty.service;

import org.springframework.http.ResponseEntity;

import com.ty.dto.RequestLogin;
import com.ty.dto.UserInputDto;
import com.ty.entity.User;
import com.ty.enums.Status;



public interface UserService {

	ResponseEntity<?> register(UserInputDto u);
	ResponseEntity<?> login(RequestLogin rl);
	ResponseEntity<?> get(Integer i);
	ResponseEntity<?> FetchAll(Integer i);
	ResponseEntity<?> updateStatus(Integer i,Integer s,Status st);
	
}
