package app.fitbuddy.dto.history;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

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

	/**
	 * Matches the yyyy-mm-dd format.
	 * Where year is limited to a range of 1900-2999.
	 * Matches a month number in a range of 01-12, and a day number in a range of 01-31.
	 *
	 * @link https://www.baeldung.com/java-date-regular-expressions
	 * Used simple implementation from the paragraph 3.2 that do not matches leap years and 30-days months.
 	 */
	@Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
	private String createdOn;

}
