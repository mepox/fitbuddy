package com.laszlojanku.fitbuddy.operation.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laszlojanku.fitbuddy.dto.RegisterDto;
import com.laszlojanku.fitbuddy.operation.service.NewUserService;
import com.laszlojanku.fitbuddy.operation.service.RegisterService;

@RestController
public class RegisterController {
	
	private final Logger logger;
	private final RegisterService registerService;
	private final NewUserService newUserService;
	
	@Autowired
	public RegisterController(RegisterService registerService, NewUserService newUserService) {
		this.registerService = registerService;
		this.newUserService = newUserService;
		this.logger = LoggerFactory.getLogger(RegisterController.class);
	}
	
	@PostMapping("/register")
	public void register(@Valid @RequestBody RegisterDto registerDto) {
		logger.info("Trying to register with: {}", registerDto);
		Integer appUserId =	registerService.register(registerDto.getName(), registerDto.getPassword());
		newUserService.addDefaultExercises(appUserId);
	}

}
