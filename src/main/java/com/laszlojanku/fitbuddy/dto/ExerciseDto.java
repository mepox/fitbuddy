package com.laszlojanku.fitbuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseDto {
	
	private Integer id;
	@NotBlank
	@Size(min = 1, max = 32)
	private String name;
	private Integer appUserId;

}
