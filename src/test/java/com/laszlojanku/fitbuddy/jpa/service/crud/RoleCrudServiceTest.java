package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
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
import com.laszlojanku.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class RoleCrudServiceTest {
	
	@InjectMocks RoleCrudService instance;
	@Mock	RoleCrudRepository roleCrudRepository;
	@Mock	RoleConverterService roleConverterService;
	
	@Test
	void readByName_whenRoleNotFound_shouldReturnNull() {
		when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
		
		RoleDto actualRoleDto = instance.readByName("roleName");
		
		assertNull(actualRoleDto);
	}
	
	@Test
	void readByname_whenRoleFound_shouldReturnRoleDto() {
		Role roleMock = RoleTestHelper.getMockRole(1, "roleName");		
		RoleDto roleDtoMock = new RoleDto(1, "roleName");
		
		when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.of(roleMock));
		when(roleConverterService.convertToDto(any(Role.class))).thenReturn(roleDtoMock);
		
		RoleDto actualRoleDto = instance.readByName("roleName");
		
		assertEquals(roleDtoMock.getId(), actualRoleDto.getId());
		assertEquals(roleDtoMock.getName(), actualRoleDto.getName());
	}
	
	@Test 
	void update_whenIdIsNull_shouldReturnNull() {
		RoleDto actualRoleDto = instance.update(null, new RoleDto(1, "roleName"));
		
		assertNull(actualRoleDto);
	}
	
	
	@Test
	void update_whenRoleDtoIsNull_shouldReturnNull() {
		Role roleMock = RoleTestHelper.getMockRole(1, "roleName");
		RoleDto roleDtoMock = new RoleDto(1, "roleName");
		
		when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.of(roleMock));
		when(roleConverterService.convertToDto(any(Role.class))).thenReturn(roleDtoMock);		
		
		RoleDto actualRoleDto = instance.update(1, null);
		
		assertNull(actualRoleDto);
	}
	
	@Test
	void update_whenExistingRoleNotFound_shouldReturnNull() {
		RoleDto newRoleDtoMock = new RoleDto(1, "newName");
		
		when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		RoleDto actualRoleDto = instance.update(1, newRoleDtoMock);
		
		assertNull(actualRoleDto);
	}
	
	@Test
	void update_whenExistingRoleFound_shouldReturnUpdatedRoleDto() {
		Role roleMock = RoleTestHelper.getMockRole(1, "oldName");		
		RoleDto roleDto = spy(new RoleDto(1, "oldName"));
		
		when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.of(roleMock));
		when(roleConverterService.convertToDto(any(Role.class))).thenReturn(roleDto);		
		
		instance.update(1, new RoleDto(1, "newRoleName"));
		
		verify(roleCrudRepository).save(any());
		verify(roleDto).setName("newRoleName");
	}
}
