package com.laszlojanku.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.exception.FitBuddyException;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.RoleCrudRepository;

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
		void whenInputIsCorrect_shouldReturnCorrectOutput() {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			Role roleMock = new Role();
			roleMock.setId(1);
			roleMock.setName("roleName");
			
			when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.of(roleMock));
			
			AppUser actualAppUser = instance.convertToEntity(appUserDtoMock);
			
			assertEquals(appUserDtoMock.getId(), actualAppUser.getId());
			assertEquals(appUserDtoMock.getName(), actualAppUser.getName());
			assertEquals(appUserDtoMock.getPassword(), actualAppUser.getPassword());
			assertEquals(appUserDtoMock.getRolename(), actualAppUser.getRole().getName());
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
		void whenInputIsCorrect_shouldReturnCorrectOutput() {
			Role roleMock = new Role();
			roleMock.setId(1);
			roleMock.setName("roleName");
			
			AppUser appUserMock = new AppUser();
			appUserMock.setId(1);
			appUserMock.setName("name");
			appUserMock.setPassword("password");
			appUserMock.setRole(roleMock);
			
			AppUserDto actualAppUserDto = instance.convertToDto(appUserMock);
			
			assertEquals(appUserMock.getId(), actualAppUserDto.getId());
			assertEquals(appUserMock.getName(), actualAppUserDto.getName());
			assertEquals(appUserMock.getPassword(), actualAppUserDto.getPassword());
			assertEquals(appUserMock.getRole().getName(), actualAppUserDto.getRolename());
		}
	}
	
	@Nested
	class ConvertAllEntity {
		@Test
		void whenInputIsNull_shouldReturnNull() {
			List<AppUserDto> actualAppUserDtos = instance.convertAllEntity(null);
			
			assertNull(actualAppUserDtos);			
		}
		
		@Test
		void whenInputIsCorrect_shouldReturnCorrectOutput() {
			Role roleMock = new Role();
			roleMock.setId(1);
			roleMock.setName("roleName");
			
			AppUser appUserMock = new AppUser();
			appUserMock.setId(1);
			appUserMock.setName("name");
			appUserMock.setPassword("password");
			appUserMock.setRole(roleMock);
			
			List<AppUser> appUserListMock = new ArrayList<>();
			appUserListMock.add(appUserMock);
			
			List<AppUserDto> actualAppUserDtoListMock = instance.convertAllEntity(appUserListMock);
			
			assertEquals(appUserListMock.size(), actualAppUserDtoListMock.size());
			assertEquals(appUserListMock.get(0).getId(), actualAppUserDtoListMock.get(0).getId());
			assertEquals(appUserListMock.get(0).getName(), actualAppUserDtoListMock.get(0).getName());
			assertEquals(appUserListMock.get(0).getPassword(), actualAppUserDtoListMock.get(0).getPassword());
			assertEquals(appUserListMock.get(0).getRole().getName(), actualAppUserDtoListMock.get(0).getRolename());
		}
	}

}
