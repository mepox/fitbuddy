package app.fitbuddy.operation.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.service.crud.AppUserCrudService;

@RestController
@RequestMapping("/user/account")
@PreAuthorize("authenticated")
public class AppUserController {
	
	private final Logger logger;
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public AppUserController(AppUserCrudService appUserCrudService) {
		this.appUserCrudService = appUserCrudService;
		this.logger = LoggerFactory.getLogger(AppUserController.class);
	}	
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public void create(@Valid @RequestBody AppUserDto appUserDto) {
		appUserCrudService.create(appUserDto);
		logger.info("Creating new appUser: {}", appUserDto);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public AppUserDto read() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return appUserCrudService.readByName(auth.getName());		
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<AppUserDto> readAll() {		
		return appUserCrudService.readMany();		
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public void update(@PathVariable("id") @NotNull Integer appUserId, @NotNull @RequestBody Map<String, String> changes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer currentUserId = appUserCrudService.readByName(auth.getName()).getId();
		if (currentUserId != null && currentUserId.equals(appUserId)) {
			appUserCrudService.update(currentUserId, changes);
		} else {
			throw new FitBuddyException("UserIds doesn't match. Cannot delete other AppUser.");
		}
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void delete(@PathVariable("id") @NotNull Integer appUserId) {		
		appUserCrudService.delete(appUserId);		
		logger.info("Deleting AppUser with ID: {}" , appUserId);
	}
}
