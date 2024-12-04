package com.ty.dto;

import lombok.Data;

@Data
public class EmailDto {
private String to;
private String cc;
private String subject;
private String body;
}
