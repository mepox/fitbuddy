package app.fitbuddy.service.operation;

import java.util.List;
import java.util.Optional;

import app.fitbuddy.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.fitbuddy.entity.AppUser;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.AppUserRepository;
import app.fitbuddy.security.AppUserPrincipal;

/**
 * Provides a service to handle the login process.
 */
@Service
public class LoginService {	
	
	private final Logger logger;
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public LoginService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.logger = LoggerFactory.getLogger(LoginService.class);
	}
	
	public void login(LoginDTO loginDTO) {
		// find the user
		Optional<AppUser> optionalAppUser = appUserRepository.findByName(loginDTO.getName());
		if (optionalAppUser.isEmpty()) {
			throw new FitBuddyException("Username not found.");
		}		
		
		// check the password
		if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), optionalAppUser.get().getPassword())) {
			throw new FitBuddyException("Incorrect password.");
		}
		
		// create the authorities
		List<GrantedAuthority> authorities = List.of(
				new SimpleGrantedAuthority(optionalAppUser.get().getRole().getName()));
		
		// create a new authentication
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				new AppUserPrincipal(optionalAppUser.get()), loginDTO.getPassword(), authorities);
		
		// create an empty SecurityContext and set the authentication
		SecurityContext context = SecurityContextHolder.createEmptyContext();		
		context.setAuthentication(authentication);
		
		// set the SecurityContext
		SecurityContextHolder.setContext(context);
		
		logger.info("Logged in: {}", optionalAppUser.get());
	}

}
