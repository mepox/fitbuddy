package app.fitbuddy.service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import app.fitbuddy.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.fitbuddy.entity.AppUser;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.AppUserRepository;
import app.fitbuddy.testhelper.AppUserTestHelper;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
	
	@InjectMocks	
	LoginService loginService;
	
	@Mock
	AppUserRepository appUserRepository;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	void usernameNotFound_throwFitBuddyException() {
		LoginDTO loginDtoMock = new LoginDTO("name", "password");
		when(appUserRepository.findByName(anyString())).thenReturn(Optional.empty());

		assertThrows(FitBuddyException.class, () -> loginService.login(loginDtoMock));
	}
	
	@Test
	void passwordDoesntMatch_throwFitBuddyException() {
		AppUser appUser = AppUserTestHelper.getMockAppUser();
		LoginDTO loginDtoMock = new LoginDTO("name", "incorrectPassword");
		when(appUserRepository.findByName(anyString())).thenReturn(Optional.of(appUser));
		when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);
		
		assertThrows(FitBuddyException.class, () -> loginService.login(loginDtoMock));
	}	
}
