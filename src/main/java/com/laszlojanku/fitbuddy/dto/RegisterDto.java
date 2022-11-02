package com.laszlojanku.fitbuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterDto {
	@NotBlank
	@Size(min = 4, max = 15)
	private final String name;
	
	@NotBlank
	@Size(min = 4, max = 15)
	private final String password;
	
}
