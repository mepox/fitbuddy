package com.laszlojanku.fitbuddy.dto;

import lombok.Data;

@Data
public class HistoryDto {
	
	private final Integer id;
	private final Integer exerciseId;
	private final Integer weight;
	private final Integer reps;
	private final Integer appUserId;

}
