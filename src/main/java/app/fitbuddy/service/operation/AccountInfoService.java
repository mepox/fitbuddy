package app.fitbuddy.service.operation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.accountinfo.AccountInfoUpdateDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.appuser.AppUserUpdateDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.service.crud.AppUserCrudService;

@Service
public class AccountInfoService {
	
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public AccountInfoService(AppUserCrudService appUserCrudService) {
		this.appUserCrudService = appUserCrudService;
	}
	
	public AppUserResponseDTO read(String name) {
		return appUserCrudService.readByName(name);
	}
	
	public void update(String name, @Valid AccountInfoUpdateDTO accountInfoUpdateDTO) {
		AppUserResponseDTO appUserResponseDTO = appUserCrudService.readByName(name);
		if (appUserResponseDTO == null) {
			throw new FitBuddyException("User not found with name: " + name);
		}
		AppUserUpdateDTO appUserUpdateDTO = new AppUserUpdateDTO(accountInfoUpdateDTO.getName(),
				accountInfoUpdateDTO.getPassword(), appUserResponseDTO.getRolename());
								
		appUserCrudService.update(appUserResponseDTO.getId(), appUserUpdateDTO);
	}
}
