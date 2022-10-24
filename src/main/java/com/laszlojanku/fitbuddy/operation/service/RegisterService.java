package com.laszlojanku.fitbuddy.operation.service;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.AppUserCrudRepository;
import com.laszlojanku.fitbuddy.jpa.repository.RoleCrudRepository;

/**
 * Provides a service to handle the registration process.
 */
@Service
public class RegisterService {
	
	private final Logger logger;
	private final AppUserCrudRepository userRepository;
	private final RoleCrudRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public RegisterService(AppUserCrudRepository userRepository, RoleCrudRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.logger = LoggerFactory.getLogger(RegisterService.class);
	}
	
	public void register(String name, String password) {
		// validate name?
		// validate password?
		
		// check if already exists
		Optional<AppUser> optional = userRepository.findByName(name);
		if (optional.isPresent()) {
			throw new EntityExistsException("Username already exists.");			
		}
				
		// encode password
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		
		// add user - with default role
		Optional<Role> userRole = roleRepository.findByName("USER");		
		AppUser appUser = new AppUser();
		appUser.setName(name);		
		appUser.setPassword(encodedPassword);
		appUser.setRole(userRole.get());
		
		userRepository.save(appUser);
		logger.info("User registered: " + appUser);
		
		// add default exercise		
	}

}
