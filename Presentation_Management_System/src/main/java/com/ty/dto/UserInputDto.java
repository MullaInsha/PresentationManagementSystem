package com.ty.dto;

import com.ty.enums.Role;
import com.ty.enums.Status;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserInputDto {

	
	private String name;
	private String email;
	private Long phone;
	private String password;
	private Status status;
	private Role role;
}
