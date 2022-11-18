package app.fitbuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDTO {
	
	@NotBlank
	@Size(min = 4, max = 15)
	private final String name;
	
	@NotBlank
	@Size(min = 4, max = 15)
	private final String password;

}
