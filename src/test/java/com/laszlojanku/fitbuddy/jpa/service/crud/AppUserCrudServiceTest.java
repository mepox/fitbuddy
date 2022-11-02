package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.AppUserCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.converter.AppUserConverterService;

@ExtendWith(MockitoExtension.class)
class AppUserCrudServiceTest {
	
	@InjectMocks	AppUserCrudService instance;
	@Mock	AppUserCrudRepository appUserCrudRepository;
	@Mock	AppUserConverterService appUserConverterService;
	
	@Test
	void readByName_whenNameIsNull_shouldReturnNull() {
		AppUserDto actualAppUserDto = instance.readByName(null);
		
		assertNull(actualAppUserDto);		
	}
	
	@Test
	void readByName_whenNameNotFound_shouldReturnNull() {
		when(appUserCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
		
		AppUserDto actualAppUserDto = instance.readByName("name");
		
		assertNull(actualAppUserDto);
	}
	
	@Test
	void update_whenIdIsNull_shouldReturnNull() {
		AppUserDto actualAppUserDto = instance.update(null, new AppUserDto(1, "name", "password", "roleName"));
		
		assertNull(actualAppUserDto);
	}
	
	@Test
	void update_whenAppUserDtoIsNull_shouldReturnNull() {
		AppUserDto actualAppUserDto = instance.update(1, null);
		
		assertNull(actualAppUserDto);
	}
	
	@Test
	void update_whenAppUserDtoNotFound_shouldReturnNull() {
		when(appUserCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		AppUserDto actualAppUserDto = instance.update(1, new AppUserDto(1, "name", "password", "roleName"));
		
		assertNull(actualAppUserDto);
	}
	
	@Test
	void update_whenAppUserDtoFound_shouldReturnTheUpdatedAppUserDto() {
		Role role = new Role();
		role.setName("roleName");
		
		AppUser appUser = new AppUser();
		appUser.setId(1);
		appUser.setName("name");
		appUser.setPassword("password");
		appUser.setRole(role);
		
		AppUserDto appUserDto = new AppUserDto(1, "name", "password", "roleName");
		
		when(appUserCrudRepository.findById(anyInt())).thenReturn(Optional.of(appUser));
		when(appUserConverterService.convertToDto(appUser)).thenReturn(appUserDto);
		when(appUserConverterService.convertToEntity(appUserDto)).thenReturn(appUser);
		
		AppUserDto newAppUserDto = new AppUserDto(1, "newName", "newPassword", "newRoleName");		
		AppUserDto actualAppUserDto = instance.update(1, newAppUserDto);
		
		verify(appUserCrudRepository).save(any(AppUser.class));		
		assertEquals(newAppUserDto.getId(), actualAppUserDto.getId());
		assertEquals(newAppUserDto.getName(), actualAppUserDto.getName());
		assertEquals(newAppUserDto.getPassword(), actualAppUserDto.getPassword());
		assertEquals(newAppUserDto.getRolename(), actualAppUserDto.getRolename());
	}

}
