package com.ty.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ty.responseStructure.ResponseStructure;

@RestControllerAdvice
public class AppException {

	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> UserNotFound(UserNotFound u){
		ResponseStructure<String> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage(u.getMessage());
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> PresentationNotFound(PresentationNotFound u){
		ResponseStructure<String> rs=new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage(u.getMessage());
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
}
