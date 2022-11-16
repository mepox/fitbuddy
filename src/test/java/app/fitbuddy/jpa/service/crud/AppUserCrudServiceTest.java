package app.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.jpa.entity.AppUser;
import app.fitbuddy.jpa.repository.AppUserCrudRepository;
import app.fitbuddy.jpa.service.converter.AppUserConverterService;
import app.fitbuddy.testhelper.AppUserTestHelper;
import app.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class AppUserCrudServiceTest {
	
	@InjectMocks	AppUserCrudService instance;
	@Mock	AppUserCrudRepository appUserCrudRepository;
	@Mock	AppUserConverterService appUserConverterService;
	
	@Nested
	class Create {
		
		@Test
		void whenAppUserDtoIsNull_shouldReturnNull() {
			AppUserDto actualAppUserDto = instance.create(null);
			
			assertNull(actualAppUserDto);
		}
		
		@Test
		void whenNameAlreadyExists_shouldReturnNull() {
			AppUser appUserMock = AppUserTestHelper.getMockAppUser();
			
			when(appUserCrudRepository.findByName(anyString())).thenReturn(Optional.of(appUserMock));
			
			AppUserDto actualAppUserDto = instance.create(new AppUserDto(1, "name", "password", "roleName"));
			
			assertNull(actualAppUserDto);
		}
		
		@Test
		void whenCorrectInput_shouldCallSave() {
			AppUserDto appUserDtoSpy = spy(new AppUserDto(1, "name", "password", "roleName"));
			
			when(appUserCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
			
			instance.create(appUserDtoSpy);
			
			verify(appUserDtoSpy).setId(null);
			verify(appUserCrudRepository).save(any());
		}
	}
	
	@Nested
	class Update {
		
		@Test
		void update_whenIdIsNull_shouldReturnNull() {			
			AppUserDto actualAppUserDto = instance.update(null, Map.of(	"name", "newName",
																		"password", "newPassword",
																		"rolename", "newRoleName"));
			
			assertNull(actualAppUserDto);
		}
		
		@Test
		void update_whenMapIsNull_shouldReturnNull() {
			AppUserDto actualAppUserDto = instance.update(1, null);
			
			assertNull(actualAppUserDto);
		}
		
		@Test
		void update_whenExistingAppUserNotFound_shouldReturnNull() {
			when(appUserCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
						
			AppUserDto actualAppUserDto = instance.update(1, Map.of("name", "newName",
																	"password", "newPassword",
																	"rolename", "newRoleName"));
			
			assertNull(actualAppUserDto);
		}
		
		@Test
		void update_whenExistingAppUserFound_shouldReturnUpdatedAppUserDto() {
			AppUser existingAppUserMock = AppUserTestHelper.getMockAppUser(1, "name", "password", RoleTestHelper.getMockRole(1, "roleName"));		
			AppUserDto existingAppUserDto = spy(new AppUserDto(1, "name", "password", "roleName"));
			
			when(appUserCrudRepository.findById(anyInt())).thenReturn(Optional.of(existingAppUserMock));
			when(appUserConverterService.convertToDto(any(AppUser.class))).thenReturn(existingAppUserDto);		
			
			instance.update(1, Map.of(	"name", "newName",
										"password", "newPassword",
										"rolename", "newRoleName"));
			
			verify(appUserCrudRepository).save(any());		
			verify(existingAppUserDto).setName("newName");
			verify(existingAppUserDto).setPassword("newPassword");
			verify(existingAppUserDto).setRolename("newRoleName");
		}		
	}
	
	@Nested
	class ReadByName {
		
		@Test
		void readByName_whenNameIsNull_shouldReturnNull() {
			AppUserDto actualAppUserDto = instance.readByName(null);
			
			assertNull(actualAppUserDto);		
		}
		
		@Test
		void readByName_whenAppUserNotFound_shouldReturnNull() {
			when(appUserCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
			
			AppUserDto actualAppUserDto = instance.readByName("name");
			
			assertNull(actualAppUserDto);
		}
		
		@Test
		void readByName_whenAppUserFound_shouldReturnAppUserDto() {
			AppUser appUserMock = AppUserTestHelper.getMockAppUser(1, "name", "password");
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			when(appUserCrudRepository.findByName(anyString())).thenReturn(Optional.of(appUserMock));
			when(appUserConverterService.convertToDto(any(AppUser.class))).thenReturn(appUserDtoMock);
			
			AppUserDto actualAppUserDto = instance.readByName("name");
			
			assertEquals(appUserDtoMock, actualAppUserDto);
		}
	}
	@Nested
	class ReadMany {
		@Test
		void readMany_whenNoUsersFound_shouldReturnEmptyList() {
			when(appUserCrudRepository.findAll()).thenReturn(Collections.emptyList());

			List<AppUserDto> actualAppUserDtos = instance.readMany();

			assertEquals(0, actualAppUserDtos.size());
		}

		@Test
		void readMany_whenUsersFound_shouldReturnListOfAppUserDtos() {
			AppUser appUserMock = AppUserTestHelper.getMockAppUser(1, "name", "password");
			List<AppUser> appUsersMock = List.of(appUserMock);

			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			List<AppUserDto> appUserDtosMock = List.of(appUserDtoMock);

			when(appUserCrudRepository.findAll()).thenReturn(appUsersMock);
			when(appUserConverterService.convertToDto(any(AppUser.class))).thenReturn(appUserDtoMock);

			List<AppUserDto> actualAppUserDtos = instance.readMany();

			assertEquals(appUserDtosMock, actualAppUserDtos);
		}
	}
}
