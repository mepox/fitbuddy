package app.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.RoleDto;
import app.fitbuddy.jpa.entity.Role;
import app.fitbuddy.jpa.service.converter.RoleConverterService;
import app.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class RoleConverterServiceTest {
	
	@InjectMocks	RoleConverterService instance;
	
	@Nested
	class ConvertToEntity {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			Role actualRole = instance.convertToEntity(null);
			
			assertNull(actualRole);
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectEntity() {
			RoleDto roleDtoMock = new RoleDto(1, "roleName");
			
			Role actualRole = instance.convertToEntity(roleDtoMock);
			
			assertTrue(RoleTestHelper.isEqual(roleDtoMock, actualRole));
		}
	}
	
	@Nested
	class ConvertToDto {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			RoleDto actualRoleDto = instance.convertToDto(null);
			
			assertNull(actualRoleDto);
		}	
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDto() {
			Role roleMock = RoleTestHelper.getMockRole();
			
			RoleDto actualRoleDto = instance.convertToDto(roleMock);
			
			assertTrue(RoleTestHelper.isEqual(roleMock, actualRoleDto));
		}
	}
	
	@Nested
	class ConvertAllEntity {
		
		@Test
		void whenInputIsNull_shouldReturnEmptyList() {
			List<RoleDto> actualRoleDtos = instance.convertAllEntity(null);
			
			assertTrue(actualRoleDtos.isEmpty());	
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDtos() {
			Role role1Mock = RoleTestHelper.getMockRole(1, "roleName1");
			Role role2Mock = RoleTestHelper.getMockRole(2, "roleName2");			
			List<Role> rolesMock = List.of(role1Mock, role2Mock);
			
			List<RoleDto> actualRoleDtos = instance.convertAllEntity(rolesMock);
			
			assertEquals(rolesMock.size(), actualRoleDtos.size());
			assertTrue(RoleTestHelper.isEqual(rolesMock.get(0), actualRoleDtos.get(0)));
			assertTrue(RoleTestHelper.isEqual(rolesMock.get(1), actualRoleDtos.get(1)));
		}
	

	}
}
