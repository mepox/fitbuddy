package com.laszlojanku.fitbuddy.operation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laszlojanku.fitbuddy.dto.RegisterDto;
import com.laszlojanku.fitbuddy.operation.service.RegisterService;

@RestController
public class RegisterController {
	
	private final Logger logger;
	private final RegisterService registerService;
	
	@Autowired
	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
		this.logger = LoggerFactory.getLogger(RegisterController.class);
	}
	
	@PostMapping("/register")
	public void register(@RequestBody RegisterDto registerDto) {
		logger.info("Trying to register with: " + registerDto);
		registerService.register(registerDto.getName(), registerDto.getPassword());
	}

}
