package app.fitbuddy.dto.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponseDTO {
	
	private Integer id;
	private Integer appUserId;
	private String exerciseName;
	private Integer weight;
	private Integer reps;
	private String createdOn;	

}
