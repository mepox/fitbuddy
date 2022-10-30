package com.laszlojanku.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.RoleDto;
import com.laszlojanku.fitbuddy.jpa.entity.Role;

@ExtendWith(MockitoExtension.class)
class RoleConverterServiceTest {
	
	@InjectMocks	RoleConverterService instance;
	
	
	@Test
	void convertToEntity_shouldReturnCorrectEntity() {
		RoleDto roleDto = new RoleDto(1, "roleName");
		
		Role actualRole = instance.convertToEntity(roleDto);
		
		assertEquals(roleDto.getId(), actualRole.getId());
		assertEquals(roleDto.getName(), actualRole.getName());
	}
	
	@Test
	void convertToEntity_whenInputIsNull_shouldReturnNull() {
		Role actualRole = instance.convertToEntity(null);
		
		assertNull(actualRole);
	}
	
	@Test
	void convertToDto_shouldReturnCorrectDto() {
		Role role = new Role();
		role.setId(1);
		role.setName("roleName");
		
		RoleDto actualRoleDto = instance.convertToDto(role);
		
		assertEquals(role.getId(), actualRoleDto.getId());
		assertEquals(role.getName(), actualRoleDto.getName());
	}
	
	@Test
	void convertToDto_whenInputIsNull_shouldReturnNull() {
		RoleDto actualRoleDto = instance.convertToDto(null);
		
		assertNull(actualRoleDto);
	}
	
	@Test
	void convertAllEntities_shouldReturnCorrectDtos() {
		Role role1 = new Role();
		role1.setId(1);
		role1.setName("roleName1");
		Role role2 = new Role();
		role2.setId(2);
		role2.setName("roleName2");
		
		List<Role> roles = Arrays.asList(role1, role2);
		
		List<RoleDto> actualRoleDtos = instance.convertAllEntity(roles);
		
		assertEquals(roles.size(), actualRoleDtos.size());
		assertEquals(roles.get(0).getId(), actualRoleDtos.get(0).getId());
		assertEquals(roles.get(0).getName(), actualRoleDtos.get(0).getName());
		assertEquals(roles.get(1).getId(), actualRoleDtos.get(1).getId());
		assertEquals(roles.get(1).getName(), actualRoleDtos.get(1).getName());		
	}
	
	@Test
	void convertAllEntities_whenInputIsNull_shouldReturnNull() {
		List<RoleDto> actualRoleDtos = instance.convertAllEntity(null);
		
		assertNull(actualRoleDtos);		
	}

}
