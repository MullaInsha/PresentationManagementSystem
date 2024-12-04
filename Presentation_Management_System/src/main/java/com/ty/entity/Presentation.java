package com.ty.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ty.enums.PresentationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "presentation_app")
public class Presentation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pid;
	
	@ManyToOne
	@JoinColumn(name="cid")
	@JsonIgnore
	private User user;
	
	
	private String course;
	
	private String topic;
	
	@Enumerated(EnumType.STRING)
	private PresentationStatus presentationStatus;
	
	private Double userTotalScore;
}
