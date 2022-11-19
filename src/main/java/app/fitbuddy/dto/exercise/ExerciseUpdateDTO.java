package app.fitbuddy.dto.exercise;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseUpdateDTO {
	
	@NotBlank
	@Size(min = 1, max = 32)
	private String name;

}
