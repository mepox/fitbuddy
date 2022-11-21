package app.fitbuddy.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.role.RoleRequestDTO;
import app.fitbuddy.dto.role.RoleResponseDTO;
import app.fitbuddy.dto.role.RoleUpdateDTO;
import app.fitbuddy.entity.Role;
import app.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class RoleMapperServiceTest {
	
	@InjectMocks
	RoleMapperService roleMapperService;
	
	@Nested
	class RequestDtoToEntity {
		
		@Test
		void requestDTOIsNull_returnNull() {
			Role actualRole = roleMapperService.requestDtoToEntity(null);
			
			assertNull(actualRole);
		}
		
		@Test
		void returnRole() {
			RoleRequestDTO requestDTO = new RoleRequestDTO("newRoleName");
			
			Role actualRole = roleMapperService.requestDtoToEntity(requestDTO);
			
			assertEquals(requestDTO.getName(), actualRole.getName());			
		}
	}
	
	@Nested
	class EntityToResponseDto {
		
		@Test
		void entityIsNull_returnNull() {
			RoleResponseDTO actualResponseDTO = roleMapperService.entityToResponseDto(null);
			
			assertNull(actualResponseDTO);
		}
		
		@Test
		void returnResponseDTO() {
			Role role = RoleTestHelper.getMockRole(1, "roleName");
			
			RoleResponseDTO actualResponseDTO = roleMapperService.entityToResponseDto(role);
			
			assertEquals(role.getId(), actualResponseDTO.getId());
			assertEquals(role.getName(), actualResponseDTO.getName());
		}		
	}
	
	@Nested
	class EntitiesToResponseDtos {
		
		@Test
		void entitiesIsNull_returnEmptyList() {
			List<RoleResponseDTO> actualResponseDTOs = roleMapperService.entitiesToResponseDtos(null);
			
			assertEquals(Collections.emptyList(), actualResponseDTOs);
		}
		
		@Test
		void entitiesIsEmpty_returnEmptyList() {
			List<RoleResponseDTO> actualResponseDTOs = roleMapperService.entitiesToResponseDtos(
					Collections.emptyList());
			
			assertEquals(Collections.emptyList(), actualResponseDTOs);			
		}
		
		@Test
		void returnResponseDTOs() {
			Role role1 = RoleTestHelper.getMockRole(1, "roleName1");
			Role role2 = RoleTestHelper.getMockRole(2, "roleName2");
			List<Role> roles = List.of(role1, role2);
			
			List<RoleResponseDTO> actualResponseDTOs = roleMapperService.entitiesToResponseDtos(roles);
			
			assertEquals(roles.size(), actualResponseDTOs.size());
			assertThat(RoleTestHelper.isEqual(roles.get(0), actualResponseDTOs.get(0))).isTrue();
			assertThat(RoleTestHelper.isEqual(roles.get(1), actualResponseDTOs.get(1))).isTrue();
		}		
	}
	
	@Nested
	class ApplyUpdateDtoToEntity {
		
		@Test
		void entityIsNull_returnNull() {
			RoleUpdateDTO updateDTO = new RoleUpdateDTO("newRoleName");
			
			Role actualRole = roleMapperService.applyUpdateDtoToEntity(null, updateDTO);
			
			assertNull(actualRole);
		}
		
		@Test
		void updateDTOIsNull_returnNull() {
			Role role = RoleTestHelper.getMockRole(1, "roleName");
			
			Role actualRole = roleMapperService.applyUpdateDtoToEntity(role, null);
			
			assertNull(actualRole);			
		}
		
		@Test
		void returnUpdatedExercise() {
			Role role = RoleTestHelper.getMockRole(1, "roleName");
			RoleUpdateDTO updateDTO = new RoleUpdateDTO("newRoleName");
			
			Role actualRole = roleMapperService.applyUpdateDtoToEntity(role, updateDTO);
			
			assertEquals(updateDTO.getName(), actualRole.getName());			
		}		
	}
}
