package app.fitbuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
	@PositiveOrZero
	private Integer weight;
	@Positive
	private Integer reps;
	private String createdOn;	

}
