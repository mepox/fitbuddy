package app.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.AppUser;
import app.fitbuddy.jpa.entity.Role;
import app.fitbuddy.jpa.repository.RoleCrudRepository;
import app.fitbuddy.jpa.service.converter.AppUserConverterService;
import app.fitbuddy.testhelper.AppUserTestHelper;
import app.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class AppUserConverterServiceTest {
	
	@InjectMocks	AppUserConverterService instance;
	@Mock	RoleCrudRepository roleCrudRepository;
	
	@Nested
	class ConvertToEntity {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			AppUser actualAppUser = instance.convertToEntity(null);
			
			assertNull(actualAppUser);
		}
		
		@Test
		void whenRoleNotFound_shouldThrowFitBuddyException() {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "role");
			
			when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
			
			assertThrows(FitBuddyException.class, () -> instance.convertToEntity(appUserDtoMock));
		}
		
		@Test
		void whenInputIsCorrect_shouldReturnCorrectEntity() {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");			
			Role roleMock = RoleTestHelper.getMockRole(1, "roleName");
			
			when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.of(roleMock));
			
			AppUser actualAppUser = instance.convertToEntity(appUserDtoMock);
			
			assertTrue(AppUserTestHelper.isEqual(appUserDtoMock, actualAppUser));
		}
	}
	
	@Nested
	class ConvertToDto {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			AppUserDto actualAppUserDto = instance.convertToDto(null);
			
			assertNull(actualAppUserDto);			
		}
		
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDto() {
			Role roleMock = RoleTestHelper.getMockRole(1, "roleName");			
			AppUser appUserMock = AppUserTestHelper.getMockAppUser(1, "name", "password", roleMock);
			
			AppUserDto actualAppUserDto = instance.convertToDto(appUserMock);
			
			assertTrue(AppUserTestHelper.isEqual(appUserMock, actualAppUserDto));
		}
	}
	
	@Nested
	class ConvertAllEntity {
		
		@Test
		void whenInputIsNull_shouldReturnEmptyList() {
			List<AppUserDto> actualAppUserDtos = instance.convertAllEntity(null);
			
			assertTrue(actualAppUserDtos.isEmpty());			
		}
		
		@Test
		void whenInputIsCorrect_shouldReturnCorrectEntityList() {
			Role roleMock = RoleTestHelper.getMockRole(1, "roleName");			
			AppUser appUserMock = AppUserTestHelper.getMockAppUser(1, "name", "password", roleMock);			
			List<AppUser> appUserListMock = List.of(appUserMock);
			
			List<AppUserDto> actualAppUserDtoListMock = instance.convertAllEntity(appUserListMock);
			
			assertEquals(appUserListMock.size(), actualAppUserDtoListMock.size());
			assertTrue(AppUserTestHelper.isEqual(appUserListMock.get(0), actualAppUserDtoListMock.get(0)));
		}
	}
}
