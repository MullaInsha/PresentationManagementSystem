package com.ty.exception;

public class PresentationNotFound extends RuntimeException{
private String message;
	
	
	public PresentationNotFound() {};
	
	
	public PresentationNotFound(String message) {
		this.message=message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
