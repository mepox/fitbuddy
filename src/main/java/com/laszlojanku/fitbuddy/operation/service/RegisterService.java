package com.laszlojanku.fitbuddy.operation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.dto.RoleDto;
import com.laszlojanku.fitbuddy.exception.FitBuddyException;
import com.laszlojanku.fitbuddy.jpa.service.crud.AppUserCrudService;
import com.laszlojanku.fitbuddy.jpa.service.crud.RoleCrudService;

/**
 * Provides a service to handle the registration process.
 */
@Service
public class RegisterService {
	
	private final Logger logger;
	private final AppUserCrudService appUserCrudService;
	private final RoleCrudService roleCrudService;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public RegisterService(AppUserCrudService appUserCrudService, RoleCrudService roleCrudService) {
		this.appUserCrudService = appUserCrudService;
		this.roleCrudService = roleCrudService;				
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.logger = LoggerFactory.getLogger(RegisterService.class);
	}
	
	public Integer register(String name, String password) {
		// check if name already exists
		if (appUserCrudService.readByName(name) != null) {
			throw new FitBuddyException("Username already exists.");			
		}
		
		// check if default user role exists
		RoleDto userRoleDto = roleCrudService.readByName("USER"); 
		if (userRoleDto == null) {
			throw new FitBuddyException("Internal server error - default role doesn't exists.");
		}
		
		// create new app user
		AppUserDto appUserDto = new AppUserDto(null, name, 
				bCryptPasswordEncoder.encode(password), userRoleDto.getName());
		
		AppUserDto newAppUserDto = appUserCrudService.create(appUserDto);
		
		logger.info("User registered: " + newAppUserDto);
		
		return newAppUserDto.getId();
	}

}
