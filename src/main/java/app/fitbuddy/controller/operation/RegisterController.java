package app.fitbuddy.controller.operation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.RegisterDTO;
import app.fitbuddy.service.operation.NewUserService;
import app.fitbuddy.service.operation.RegisterService;

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
	public void register(@Valid @RequestBody RegisterDTO registerDTO) {
		logger.info("Trying to register with: {}", registerDTO);
		Integer appUserId =	registerService.register(registerDTO.getName(), registerDTO.getPassword());
		newUserService.addDefaultExercises(appUserId);
	}

}
