package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.role.RoleRequestDTO;
import app.fitbuddy.dto.role.RoleUpdateDTO;
import app.fitbuddy.entity.Role;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.RoleRepository;
import app.fitbuddy.service.mapper.RoleMapperService;
import app.fitbuddy.testhelper.RoleTestHelper;

@ExtendWith(MockitoExtension.class)
class RoleCrudServiceTest {
	
	@InjectMocks
	RoleCrudService roleCrudService;
	
	@Mock
	RoleRepository roleRepository;
	
	@Mock
	RoleMapperService roleMapperService;
	
	@Nested
	class Create {
		
		@Test
		void requestDTOIsNull_returnNull() {
			assertNull(roleCrudService.create(null));
		}
		
		@Test
		void nameAlreadyExists_throwFitBuddyException() {
			RoleRequestDTO requestDTO = new RoleRequestDTO("roleName");
			Role role = RoleTestHelper.getMockRole();
			
			when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
			
			assertThrows(FitBuddyException.class, () -> roleCrudService.create(requestDTO));			
		}
		
		@Test
		void callSave() {
			RoleRequestDTO requestDTO = new RoleRequestDTO("roleName");
			Role role = RoleTestHelper.getMockRole();
			
			when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
			when(roleMapperService.requestDtoToEntity(requestDTO)).thenReturn(role);
			
			roleCrudService.create(requestDTO);
			
			verify(roleRepository).save(role);
		}		
	}
	
	@Nested
	class ReadById {
		
		@Test
		void notFoundById_returnNull() {
			when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertNull(roleCrudService.readById(1));			
		}		
	}
	
	@Nested
	class ReadByName {
		
		@Test
		void notFoundByName_returnNull() {
			when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
			
			assertNull(roleCrudService.readByName("roleName"));
		}		
	}
	
	@Nested
	class ReadAll {
		
		@Test
		void callAppUserRepository() {
			roleCrudService.readAll();
			
			verify(roleRepository).findAll();
		}		
	}
	
	@Nested
	class Update {
		
		@Test
		void updateDTOIsNull_returnNull() {	
			assertNull(roleCrudService.update(1, null));			
		}
		
		@Test
		void existingRoleNotFound_returnNull() {
			RoleUpdateDTO updateDTO = new RoleUpdateDTO("newRoleName");
			
			when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertNull(roleCrudService.update(1, updateDTO));
		}
		
		@Test
		void nameAlreadyExists_throwFitBuddyException() {
			RoleUpdateDTO updateDTO = new RoleUpdateDTO("newRoleName");
			Role role = RoleTestHelper.getMockRole();
			
			when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
			when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
			
			assertThrows(FitBuddyException.class, () -> roleCrudService.update(1, updateDTO));			
		}
		
		@Test
		void applyAndSave() {
			RoleUpdateDTO updateDTO = new RoleUpdateDTO("newRoleName");
			Role role = RoleTestHelper.getMockRole();
			
			when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
			when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
			when(roleMapperService.applyUpdateDtoToEntity(any(Role.class), any(RoleUpdateDTO.class))).thenReturn(role);
			
			roleCrudService.update(1, updateDTO);
			
			verify(roleMapperService).applyUpdateDtoToEntity(role, updateDTO);
			verify(roleRepository).save(role);						
		}		
	}
	
	@Nested
	class Delete {
		
		@Test
		void callDeleteById() {
			roleCrudService.delete(111);
			
			verify(roleRepository).deleteById(111);
		}
	}
}
