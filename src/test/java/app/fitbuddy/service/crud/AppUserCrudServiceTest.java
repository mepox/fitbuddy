package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.appuser.AppUserRequestDTO;
import app.fitbuddy.repository.AppUserRepository;
import app.fitbuddy.service.mapper.AppUserMapperService;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.appuser.AppUserUpdateDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.testhelper.AppUserTestHelper;

@ExtendWith(MockitoExtension.class)
class AppUserCrudServiceTest {
	
	@InjectMocks
	AppUserCrudService appUserCrudService;
	
	@Mock
	AppUserRepository appUserRepository;
	
	@Mock
	AppUserMapperService appUserMapperService;
	
	@Nested
	class Create {
		
		@Test
		void callSave() {
			AppUserRequestDTO appUserRequestDTO = new AppUserRequestDTO("name", "password", "roleName");
			
			when(appUserRepository.findByName(anyString())).thenReturn(Optional.empty());
			when(appUserMapperService.requestDtoToEntity(appUserRequestDTO)).thenReturn(null);
			
			appUserCrudService.create(appUserRequestDTO);
			
			verify(appUserRepository).save(null);
		}
		
		@Test
		void returnResponseDTO() {
			AppUserRequestDTO appUserRequestDTO = new AppUserRequestDTO("name", "password", "roleName");
			AppUser appUser = AppUserTestHelper.getMockAppUser();
			
			when(appUserRepository.findByName(anyString())).thenReturn(Optional.empty());
			when(appUserMapperService.requestDtoToEntity(appUserRequestDTO)).thenReturn(appUser);
			
			appUserCrudService.create(appUserRequestDTO);
			
			verify(appUserRepository).save(appUser);
		}
		
		@Test
		void requestDTOIsNull_returnNull() {
			AppUserResponseDTO actualResponseDTO = appUserCrudService.create(null);
			
			assertNull(actualResponseDTO);
		}
		
		@Test
		void nameAlreadyExists_throwFitBuddyException() {
			AppUserRequestDTO appUserRequestDTO = new AppUserRequestDTO("name", "password", "roleName");
			AppUser appUser = AppUserTestHelper.getMockAppUser();
			
			when(appUserRepository.findByName(anyString())).thenReturn(Optional.of(appUser));
			
			assertThrows(FitBuddyException.class, () -> appUserCrudService.create(appUserRequestDTO));
		}
	}
	
	@Nested
	class ReadById {

		@Test
		void notFoundById_returnNull() {
			when(appUserRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			AppUserResponseDTO actualResponseDTO = appUserCrudService.readById(1);
			
			assertNull(actualResponseDTO);			
		}
	}
	
	@Nested
	class ReadByName {

		@Test
		void notFoundByName_returnNull() {
			when(appUserRepository.findByName(anyString())).thenReturn(Optional.empty());

			AppUserResponseDTO actualResponseDTO = appUserCrudService.readByName("name");

			assertNull(actualResponseDTO);
		}
	}
	
	@Nested
	class ReadAll {
		
		@Test
		void returnListOfResponseDTOs() {
			AppUser appUser1 = AppUserTestHelper.getMockAppUser();
			AppUser appUser2 = AppUserTestHelper.getMockAppUser();
			List<AppUser> appUsers = List.of(appUser1, appUser2);
			AppUserResponseDTO responseDTO_1 = new AppUserResponseDTO(1, "name1", "password1", "roleName1");
			AppUserResponseDTO responseDTO_2 = new AppUserResponseDTO(2, "name2", "password2", "roleName2");
			List<AppUserResponseDTO> responseDTOs = List.of(responseDTO_1, responseDTO_2);
			
			when(appUserRepository.findAll()).thenReturn(appUsers);
			when(appUserMapperService.entitiesToResponseDtos(appUsers)).thenReturn(responseDTOs);
			
			List<AppUserResponseDTO> actualResponseDTOs = appUserCrudService.readAll();
			
			assertEquals(responseDTOs.size(), actualResponseDTOs.size());
			assertEquals(responseDTOs.get(0), actualResponseDTOs.get(0));
			assertEquals(responseDTOs.get(1), actualResponseDTOs.get(1));
		}
	}
	
	@Nested
	class Update {
		
		@Test
		void updateDTOIsNull_returnNull() {
			AppUserResponseDTO actualResponseDTO = appUserCrudService.update(1, null);
			
			assertNull(actualResponseDTO);
		}
		
		@Test
		void existingAppUserNotFound_returnNull() {
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO("name", "roleName");
			
			when(appUserRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			AppUserResponseDTO actualResponseDTO = appUserCrudService.update(1, updateDTO);
			
			assertNull(actualResponseDTO);
		}
		
		@Test
		void nameAlreadyExists_throwFitBuddyException() {
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO("name", "roleName");			
			AppUser appUser = AppUserTestHelper.getMockAppUser();
			
			when(appUserRepository.findById(anyInt())).thenReturn(Optional.of(appUser));
			when(appUserRepository.findByName(anyString())).thenReturn(Optional.of(appUser));
			
			assertThrows(FitBuddyException.class, () -> appUserCrudService.update(1, updateDTO));
		}
		
		@Test
		void callSave() {
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO("name", "roleName");
			AppUser appUser = AppUserTestHelper.getMockAppUser();
			
			when(appUserRepository.findById(anyInt())).thenReturn(Optional.of(appUser));
			when(appUserRepository.findByName(anyString())).thenReturn(Optional.empty());
			when(appUserMapperService.applyUpdateDtoToEntity(appUser, updateDTO)).thenReturn(appUser);
			
			appUserCrudService.update(1, updateDTO);
			
			verify(appUserRepository).save(appUser);
		}
	}
	
	@Nested
	class Delete {
		
		@Test
		void callDeleteById() {
			appUserCrudService.delete(111);
			
			verify(appUserRepository).deleteById(111);
		}
	}
}
