package app.fitbuddy.operation.service;

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

import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.dto.RoleDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.service.crud.AppUserCrudService;
import app.fitbuddy.jpa.service.crud.RoleCrudService;
import app.fitbuddy.operation.service.RegisterService;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {
	
	@InjectMocks	RegisterService instance;
	@Mock	AppUserCrudService appUserCrudService;
	@Mock	RoleCrudService	roleCrudService;
	
	@Test
	void register_whenNameAlreadyExists_shouldThrowFitBuddyException() {
		AppUserDto appUserDto = new AppUserDto(1, "name", "password", "roleName");
		
		when(appUserCrudService.readByName(anyString())).thenReturn(appUserDto);
		
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
		RoleDto userRoleDto = new RoleDto(1, "roleName");
		AppUserDto newAppUserDto = new AppUserDto(123, "name", "password", "roleName");		
		
		when(appUserCrudService.readByName(anyString())).thenReturn(null);
		when(roleCrudService.readByName(anyString())).thenReturn(userRoleDto);
		when(appUserCrudService.create(any(AppUserDto.class))).thenReturn(newAppUserDto);
		
		Integer newAppUserId = instance.register("name", "password");
		
		assertEquals(123, newAppUserId);
	}

}
