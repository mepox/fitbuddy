package app.fitbuddy.dto.role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateDTO {
	
	@NotBlank
	@Size(min = 4, max = 16)
	private String name;

}
