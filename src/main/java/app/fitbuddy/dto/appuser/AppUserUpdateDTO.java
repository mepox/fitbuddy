package app.fitbuddy.dto.appuser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@Size(min = 4, max = 15)
	private String password;

	@NotBlank
	@Size(min = 4, max = 16)
	private String rolename;
	
	public AppUserUpdateDTO(String name, String rolename) {
		this.name = name;
		this.rolename = rolename;
	}

}
