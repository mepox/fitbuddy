package com.laszlojanku.fitbuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryDto {
	
	private Integer id;
	private Integer appUserId;
	@NotBlank
	@Size(min = 1, max = 32)
	private String exerciseName;
	private Integer weight;
	private Integer reps;
	private String createdOn;	

}
