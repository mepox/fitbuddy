package app.fitbuddy.dto.appuser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserUpdateDTO {
	
	@NotBlank
	@Size(min = 4, max = 15)
	private String name;

	@NotBlank
	@Size(min = 4, max = 16)
	private String rolename;

}
