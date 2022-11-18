package app.fitbuddy.service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.service.crud.AppUserCrudService;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
	
	@InjectMocks	LoginService instance;
	@Mock	AppUserCrudService appUserCrudService;	
	
	@Test
	void login_whenUsernameNotFound_shouldThrowFitBuddyException() {
		when(appUserCrudService.readByName(anyString())).thenReturn(null);
		
		assertThrows(FitBuddyException.class, () -> instance.login("name", "password"));
	}
	
	@Test
	void login_whenPasswordDoesntMatch_shouldThrowFitBuddyException() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		AppUserResponseDTO appUserDtoMock = new AppUserResponseDTO(1, "name", 
				bCryptPasswordEncoder.encode("userPassword"), "roleName");
		
		when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
		
		assertThrows(FitBuddyException.class, () -> instance.login("name", "incorrectPassword"));		
	}
	
	@Test
	void login_whenSuccess_shouldntThrowException() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();		
		AppUserResponseDTO appUserDtoMock = new AppUserResponseDTO(1, "name",
				bCryptPasswordEncoder.encode("userPassword"), "roleName");
		
		when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);		
		
		assertDoesNotThrow(() -> instance.login("name", "userPassword"));
	}
	
}
