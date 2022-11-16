package app.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.RoleDto;
import app.fitbuddy.jpa.entity.Role;
import app.fitbuddy.jpa.repository.RoleCrudRepository;
import app.fitbuddy.jpa.service.converter.RoleConverterService;
import app.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class RoleCrudServiceTest {
	
	@InjectMocks RoleCrudService instance;
	@Mock	RoleCrudRepository roleCrudRepository;
	@Mock	RoleConverterService roleConverterService;
	
	@Nested
	class Create {
		
		@Test
		void whenRoleDtoIsNull_shouldReturnNull() {
			RoleDto actualRoleDto = instance.create(null);
			
			assertNull(actualRoleDto);
		}
		
		@Test
		void whenRoleNameAlreadyExists_shouldReturnNull() {
			Role roleMock = RoleTestHelper.getMockRole();
			
			when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.of(roleMock));
			
			RoleDto actualRoleDto = instance.create(new RoleDto(1, "roleName"));
			
			assertNull(actualRoleDto);			
		}
		
		@Test
		void whenCorrectInput_shouldCallSave() {
			RoleDto roleDtoSpy = spy(new RoleDto(1, "roleName"));
			
			when(roleCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
			
			instance.create(roleDtoSpy);
			
			verify(roleDtoSpy).setId(null);
			verify(roleCrudRepository).save(any());
		}
	}
	
	@Nested
	class Update {
		
		@Test 
		void update_whenIdIsNull_shouldReturnNull() {
			RoleDto actualRoleDto = instance.update(null, Map.of("name", "newRoleName"));
			
			assertNull(actualRoleDto);
		}
		
		
		@Test
		void update_whenMapIsNull_shouldReturnNull() {
			Role roleMock = RoleTestHelper.getMockRole(1, "roleName");
			RoleDto roleDtoMock = new RoleDto(1, "roleName");
			
			when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.of(roleMock));
			when(roleConverterService.convertToDto(any(Role.class))).thenReturn(roleDtoMock);		
			
			RoleDto actualRoleDto = instance.update(1, null);
			
			assertNull(actualRoleDto);
		}
		
		@Test
		void update_whenExistingRoleNotFound_shouldReturnNull() {
			when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			RoleDto actualRoleDto = instance.update(1, Map.of("name", "newRoleName"));
			
			assertNull(actualRoleDto);
		}
		
		@Test
		void update_whenExistingRoleFound_shouldReturnUpdatedRoleDto() {
			Role roleMock = RoleTestHelper.getMockRole(1, "oldName");		
			RoleDto roleDto = spy(new RoleDto(1, "oldName"));
			
			when(roleCrudRepository.findById(anyInt())).thenReturn(Optional.of(roleMock));
			when(roleConverterService.convertToDto(any(Role.class))).thenReturn(roleDto);		
			
			instance.update(1, Map.of("name", "newRoleName"));
			
			verify(roleCrudRepository).save(any());
			verify(roleDto).setName("newRoleName");
		}		
	}
	
	@Nested
	class ReadByName {
		
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
	}
}
