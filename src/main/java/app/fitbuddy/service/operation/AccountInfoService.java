package app.fitbuddy.service.operation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.fitbuddy.config.DemoUserProperties;
import app.fitbuddy.dto.accountinfo.AccountInfoResponseDTO;
import app.fitbuddy.dto.accountinfo.AccountInfoUpdateDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.appuser.AppUserUpdateDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.service.crud.AppUserCrudService;

@Service
public class AccountInfoService {
	
	private final AppUserCrudService appUserCrudService;
	private final DemoUserProperties demoUserProperties;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public AccountInfoService(AppUserCrudService appUserCrudService, DemoUserProperties demoUserProperties,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserCrudService = appUserCrudService;
		this.demoUserProperties = demoUserProperties;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public AccountInfoResponseDTO read(String name) {
		return new AccountInfoResponseDTO(appUserCrudService.readByName(name).getName());
	}
	
	public void update(String name, @Valid AccountInfoUpdateDTO accountInfoUpdateDTO) {
		if (name.equals(demoUserProperties.getName())) {
			throw new FitBuddyException("Demo user's password cannot be changed.");
		}
		AppUserResponseDTO appUserResponseDTO = appUserCrudService.readByName(name);
		if (appUserResponseDTO == null) {
			throw new FitBuddyException("User not found with name: " + name);
		}
		if (!bCryptPasswordEncoder.matches(accountInfoUpdateDTO.getOldPassword(), appUserResponseDTO.getPassword())) {
			throw new FitBuddyException("Old password is not correct.");
		}
		appUserCrudService.update(appUserResponseDTO.getId(), new AppUserUpdateDTO(appUserResponseDTO.getName(), 
				accountInfoUpdateDTO.getNewPassword(), appUserResponseDTO.getRolename()));
	}
}
