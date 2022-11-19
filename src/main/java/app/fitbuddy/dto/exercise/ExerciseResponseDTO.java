package app.fitbuddy.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponseDTO {
	
	private Integer id;
	private String name;	
	private Integer appUserId;

}
