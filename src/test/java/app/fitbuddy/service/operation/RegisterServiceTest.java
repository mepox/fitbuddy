package app.fitbuddy.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.appuser.AppUserRequestDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.role.RoleResponseDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.service.crud.AppUserCrudService;
import app.fitbuddy.service.crud.RoleCrudService;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {
	
	@InjectMocks	RegisterService instance;
	@Mock	AppUserCrudService appUserCrudService;
	@Mock	RoleCrudService	roleCrudService;
	
	@Test
	void register_whenNameAlreadyExists_shouldThrowFitBuddyException() {
		AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(1, "name", "password", "roleName");
		
		when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);
		
		assertThrows(FitBuddyException.class, () -> instance.register("name", "password"));
	}
	
	@Test
	void register_whenUserRoleDoesntExists_shouldThrowFitBuddyException() {
		
		when(appUserCrudService.readByName(anyString())).thenReturn(null);
		when(roleCrudService.readByName(anyString())).thenReturn(null);
		
		assertThrows(FitBuddyException.class, () -> instance.register("name", "password"));		
	}
	
	@Test
	void register_whenRegisterNewUser_shouldReturnNewUserId() {
		RoleResponseDTO roleResponseDTO = new RoleResponseDTO(1, "roleName");
		AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(123, "name", "encodedPassword", "roleName");		
		
		when(appUserCrudService.readByName(anyString())).thenReturn(null);
		when(roleCrudService.readByName(anyString())).thenReturn(roleResponseDTO);
		when(appUserCrudService.create(any(AppUserRequestDTO.class))).thenReturn(appUserResponseDTO);
		
		Integer newAppUserId = instance.register("name", "password");
		
		assertEquals(123, newAppUserId);
	}

}
