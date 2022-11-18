package app.fitbuddy.dto.exercise;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequestDTO {
	
	@NotBlank
	@Size(min = 1, max = 32)
	private String name;
	
	@JsonIgnore	
	private Integer appUserId;
	
}
