package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.RoleDto;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.RoleCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.converter.RoleConverterService;

@ExtendWith(MockitoExtension.class)
class RoleCrudServiceTest {
	
	@InjectMocks RoleCrudService instance;
	@Mock	RoleCrudRepository roleCrudRepository;
	@Mock	RoleConverterService roleConverterService;
	
	@Test
	void update_shouldReturnUpdatedRoleDto() {
		Role oldRole = new Role();
		oldRole.setId(1);
		oldRole.setName("oldName");
		
		RoleDto oldRoleDto = new RoleDto(1, "oldName");
		
		RoleDto newDto = new RoleDto(1, "newName");
		
		when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.of(oldRole));
		when(roleConverterService.convertToDto(any(Role.class))).thenReturn(oldRoleDto);
		
		RoleDto actualRoleDto = instance.update(1, newDto);
		
		assertEquals(newDto.getId(), actualRoleDto.getId());
		assertEquals(newDto.getName(), actualRoleDto.getName());
	}
	
	@Test
	void update_whenExistingRoleNotFound_shouldReturnNull() {
		RoleDto newDto = new RoleDto(1, "newName");
		
		when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		RoleDto actualRoleDto = instance.update(1, newDto);
		
		assertNull(actualRoleDto);
	}
	
	@Test
	void readByName_whenRoleNotFound_shouldReturnNull() {
		when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
		
		RoleDto actualRoleDto = instance.readByName("roleName");
		
		assertNull(actualRoleDto);
	}
	
	@Test
	void readByname_whenRoleFound_shouldReturnRoleDto() {
		Role role = new Role();
		role.setId(1);
		role.setName("roleName");
		
		RoleDto roleDto = new RoleDto(1, "roleName");
		
		when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.of(role));
		when(roleConverterService.convertToDto(any(Role.class))).thenReturn(roleDto);
		
		RoleDto actualRoleDto = instance.readByName("roleName");
		
		assertEquals(roleDto.getId(), actualRoleDto.getId());
		assertEquals(roleDto.getName(), actualRoleDto.getName());
	}

}
