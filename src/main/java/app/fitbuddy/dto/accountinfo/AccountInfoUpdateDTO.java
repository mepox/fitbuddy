package app.fitbuddy.dto.accountinfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoUpdateDTO {
	
	@NotBlank
	@Size(min = 4, max = 15)
	private String oldPassword;
	
	@NotBlank
	@Size(min = 4, max = 15)
	private String newPassword;

}
