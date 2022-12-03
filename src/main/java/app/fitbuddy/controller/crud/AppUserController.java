package app.fitbuddy.controller.crud;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.appuser.AppUserRequestDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.appuser.AppUserUpdateDTO;
import app.fitbuddy.security.AppUserPrincipal;
import app.fitbuddy.service.crud.AppUserCrudService;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AppUserController {
	
	private final Logger logger;
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public AppUserController(AppUserCrudService appUserCrudService) {
		this.appUserCrudService = appUserCrudService;
		this.logger = LoggerFactory.getLogger(AppUserController.class);
	}	
	
	@PostMapping
	public void create(@Valid @RequestBody AppUserRequestDTO appUserRequestDTO) {
		appUserCrudService.create(appUserRequestDTO);
		logger.info("Creating new appUser: {}", appUserRequestDTO);
	}	
	
	@GetMapping
	public List<AppUserResponseDTO> readAll() {		
		return appUserCrudService.readAll();		
	}
	
	@PutMapping("{id}")
	public void update(@PathVariable("id") @NotNull Integer appUserId, 
			@Valid @RequestBody AppUserUpdateDTO appUserUpdateDTO, 
			@AuthenticationPrincipal AppUserPrincipal appUserPrincipal) {
		appUserCrudService.update(appUserPrincipal.getId(), appUserUpdateDTO);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") @NotNull Integer appUserId) {		
		appUserCrudService.delete(appUserId);		
		logger.info("Deleting AppUser with ID: {}" , appUserId);
	}
}
