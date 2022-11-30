package app.fitbuddy.dto.history;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import app.fitbuddy.annotation.FitBuddyDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRequestDTO {
	
	@JsonIgnore	
	private Integer appUserId;
	
	@NotBlank
	@Size(min = 1, max = 32)
	private String exerciseName;	
	
	@PositiveOrZero
	private Integer weight;	
	
	@Positive
	private Integer reps;
	
	@FitBuddyDate
	private String createdOn;

}
