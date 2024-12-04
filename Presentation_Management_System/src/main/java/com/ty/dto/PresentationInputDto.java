package com.ty.dto;

import com.ty.enums.PresentationStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PresentationInputDto {

	
	
	private String course;
	private String topic;
	private PresentationStatus presentationStatus;
}
