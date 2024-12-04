package com.ty.dto;

import com.ty.enums.Role;
import com.ty.enums.Status;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
	private String name;
	private String email;
	private Role role;
	private Status status;
}
