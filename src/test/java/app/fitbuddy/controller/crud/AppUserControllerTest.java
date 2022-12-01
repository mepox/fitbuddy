package app.fitbuddy.controller.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.fitbuddy.FitBuddyApplication;
import app.fitbuddy.config.SecurityConfig;
import app.fitbuddy.dto.appuser.AppUserRequestDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.appuser.AppUserUpdateDTO;
import app.fitbuddy.service.crud.AppUserCrudService;
import app.fitbuddy.testhelper.annotation.WithMockAppUserPrincipal;

@WebMvcTest(AppUserController.class)
@ContextConfiguration(classes = {FitBuddyApplication.class, SecurityConfig.class})
class AppUserControllerTest {
		
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	AppUserCrudService appUserCrudService;
	
	final String API_PATH = "/users";
	
	@Nested
	@WithMockAppUserPrincipal(authority = "ADMIN")
	class Create {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {			
			AppUserRequestDTO requestDTO = new AppUserRequestDTO("name", "password", "rolename");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().is(302));
		}
		
		@Test
		void whenAuthedWithAdmin_shouldReturnOk() throws Exception {
			AppUserRequestDTO requestDTO = new AppUserRequestDTO("name", "password", "rolename");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().isOk());
			
			verify(appUserCrudService).create(any(AppUserRequestDTO.class));
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldReturnForbidden403() throws Exception {
			AppUserRequestDTO requestDTO = new AppUserRequestDTO("name", "password", "rolename");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().isForbidden());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"nam", "namenamenamename"}) // <4 and >15 characters
		void whenNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
			AppUserRequestDTO requestDTO = new AppUserRequestDTO(name, "password", "rolename");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"pas", "passwordpassword"}) // <4 and >15 characters
		void whenPasswordSizeNotCorrect_shouldReturnBadRequest(String password) throws Exception {			
			AppUserRequestDTO requestDTO = new AppUserRequestDTO("name", password, "rolename");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"rol", "roleNameroleNamer"}) // <4 and >16 characters
		void whenRoleNameSizeNotCorrect_shouldReturnBadRequest(String roleName) throws Exception {			
			AppUserRequestDTO requestDTO = new AppUserRequestDTO("name", "password", roleName);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().isBadRequest());
		}
	}	
	
	@Nested
	@WithMockAppUserPrincipal(authority = "ADMIN")
	class ReadMany {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(get(API_PATH)).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldReturnForbidden() throws Exception {
			mockMvc.perform(get(API_PATH)).andExpect(status().isForbidden());
		}
		
		@Test
		void whenAuthedWithAdmin_shouldReturnAppUserDtoList() throws Exception {
			AppUserResponseDTO responseDTO_1 = new AppUserResponseDTO(1, "name", null, "roleName");
			AppUserResponseDTO responseDTO_2 = new AppUserResponseDTO(2, "name", null, "roleName");
			List<AppUserResponseDTO> responseDTOs = List.of(responseDTO_1, responseDTO_2);
			
			when(appUserCrudService.readAll()).thenReturn(responseDTOs);
			
			MvcResult mvcResult = mockMvc.perform(get(API_PATH)).andExpect(status().isOk()).andReturn();
			
			List<AppUserResponseDTO> actualResponseDTOs = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<AppUserResponseDTO>>() {});
			
			assertEquals(responseDTOs.size(), actualResponseDTOs.size());
			assertEquals(responseDTOs.get(0), actualResponseDTOs.get(0));
			assertEquals(responseDTOs.get(1), actualResponseDTOs.get(1));
		}
	}
	
	@Nested
	@WithMockAppUserPrincipal(authority = "ADMIN")
	class Update {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(put(API_PATH + "/1")).andExpect(status().is(302));
		}
		
		@Test
		void whenPathVariableNotInteger_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(put(API_PATH + "/abc")).andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"nam", "namenamenamename"}) // <4 and >15 characters
		void whenNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {			
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO(name, "roleName");
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updateDTO)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"rol", "roleNameroleNamer"}) // <4 and >16 characters
		void whenRoleNameSizeNotCorrect_shouldReturnBadRequest(String roleName) throws Exception {
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO("name", roleName);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updateDTO)))
			.andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldForbidden() throws Exception {
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO("newName", "newRoleName");			
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updateDTO)))
			.andExpect(status().isForbidden());
		}
		
		@Test
		void whenAuthedWithAdmin_shouldReturnOk() throws Exception {
			AppUserUpdateDTO updateDTO = new AppUserUpdateDTO("newName", "newRoleName");
			AppUserResponseDTO responseDTO = new AppUserResponseDTO(1, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(responseDTO);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(updateDTO)))
			.andExpect(status().isOk());
			
			verify(appUserCrudService).update(1, updateDTO);
		}
	}
	
	@Nested
	@WithMockAppUserPrincipal(authority = "ADMIN")
	class Delete {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().is(302));
		}
		
		@Test
		void whenPathVariableNotInteger_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(delete(API_PATH + "/abc")).andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldReturnForbidden() throws Exception {
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isForbidden());
		}
		
		@Test
		void whenAuthedWithAdmin_shouldCallDelete() throws Exception {
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isOk());
			
			verify(appUserCrudService).delete(1);
		}
	}
}
