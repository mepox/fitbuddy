package app.fitbuddy.dto.appuser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestDTO {
	
	@NotBlank
	@Size(min = 4, max = 15)
	private String name;
	
	@NotBlank
	@Size(min = 4, max = 15)
	private String password;
	
	@NotBlank
	@Size(min = 4, max = 16)
	private String rolename;

}
