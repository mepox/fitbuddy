package app.fitbuddy.controller.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.LoginDTO;
import app.fitbuddy.service.operation.LoginService;

import javax.validation.Valid;

/**
 * Handles the client's REST API requests that are related to logging in.
 */
@RestController
public class LoginController {
	
	private final Logger logger;	
	private final LoginService loginService;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
		this.logger = LoggerFactory.getLogger(LoginController.class);
	}
		
	@PostMapping("/login/perform_login")
	public void login(@Valid @RequestBody LoginDTO loginDTO) {
		logger.info("Trying to log in: {}", loginDTO);
		loginService.login(loginDTO);
	}
	
}
