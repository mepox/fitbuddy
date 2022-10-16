package com.laszlojanku.fitbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseDto {
	
	private Integer id;
	private String name;
	private Integer appUserId;

}
