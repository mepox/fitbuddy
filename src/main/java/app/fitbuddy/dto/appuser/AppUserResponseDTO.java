package app.fitbuddy.dto.appuser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseDTO {
	
	private Integer id;
	private String name;
	
	@JsonIgnore
	private String password;
	
	private String rolename;
	
}
