package com.laszlojanku.fitbuddy.operation.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.exception.FitBuddyException;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.AppUserCrudRepository;
import com.laszlojanku.fitbuddy.jpa.repository.RoleCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.converter.AppUserConverterService;

/**
 * Provides a service to handle the registration process.
 */
@Service
public class RegisterService {
	
	private final Logger logger;
	private final AppUserCrudRepository userRepository;
	private final RoleCrudRepository roleRepository;
	private final AppUserConverterService converterService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public RegisterService(AppUserCrudRepository userRepository, RoleCrudRepository roleRepository,
			AppUserConverterService converterService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.converterService = converterService;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.logger = LoggerFactory.getLogger(RegisterService.class);
	}
	
	public Integer register(String name, String password) {
		// check if already exists
		Optional<AppUser> optional = userRepository.findByName(name);
		if (optional.isPresent()) {
			throw new FitBuddyException("Username already exists.");			
		}
		
		// add user - with default role
		Optional<Role> userRole = roleRepository.findByName("USER");		
		AppUser appUser = new AppUser();
		appUser.setName(name);		
		appUser.setPassword(bCryptPasswordEncoder.encode(password));
		appUser.setRole(userRole.get());
		
		logger.info("User registered: " + appUser);
		return converterService.convertToDto(userRepository.save(appUser)).getId();
	}

}
