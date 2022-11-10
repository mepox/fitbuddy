package app.fitbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryDto {
	
	private Integer id;
	private Integer appUserId;
	private String exerciseName;
	private Integer weight;
	private Integer reps;
	private String createdOn;	

}
