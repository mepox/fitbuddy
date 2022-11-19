package app.fitbuddy.dto.appuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseDTO {
	
	private Integer id;
	private String name;
	private String password;
	private String rolename;
	
}
