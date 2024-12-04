package com.ty.dto;

import com.ty.enums.PresentationStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PresentationDto {
	
	
	private String course;
	
	private String topic;
	
	private PresentationStatus presentationStatus;
	
	private Double userTotalScore;

}
