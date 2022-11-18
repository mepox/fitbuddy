package app.fitbuddy.dto.history;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryUpdateDTO {
	
	@PositiveOrZero
	private Integer weight;
	
	@Positive
	private Integer reps;

}
