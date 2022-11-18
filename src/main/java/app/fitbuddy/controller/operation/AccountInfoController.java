package app.fitbuddy.controller.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.service.crud.AppUserCrudService;

@RestController
@RequestMapping("/user/account")
@PreAuthorize("authenticated")
public class AccountInfoController {
	
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public AccountInfoController(AppUserCrudService appUserCrudService) {
		this.appUserCrudService = appUserCrudService;
	}
	
	@GetMapping
	public AppUserResponseDTO read() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return appUserCrudService.readByName(auth.getName());
	}

}
