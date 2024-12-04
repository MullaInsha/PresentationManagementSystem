package com.ty.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ty.enums.Role;
import com.ty.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users_app") 
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	private String name;
	
	@Column(unique=true)
	private String email;
	
	private Long phone;
	
	@NotBlank(message = "Password is Required")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$",
	message = "Password must contain at least 8 characters, one digit, one lowercase letter, one uppercase letter, and one special character")
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy="user")
	@JsonIgnore
	private List<Presentation> presentation;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	
	private Double userTotalScore;
}
