package app.fitbuddy.operation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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
import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.jpa.service.crud.AppUserCrudService;
import app.fitbuddy.operation.controller.AppUserController;

@WebMvcTest(AppUserController.class)
@ContextConfiguration(classes = {FitBuddyApplication.class, SecurityConfig.class})
class AppUserControllerTest {
		
	@Autowired	MockMvc mockMvc;
	@Autowired	ObjectMapper objectMapper;
	@MockBean	AppUserCrudService appUserCrudService;
	
	final String API_PATH = "/user/account";
	
	@Nested
	class Create {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"ADMIN"})
		void whenAuthedWithAdmin_shouldReturnOk() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isOk());
			
			verify(appUserCrudService).create(appUserDtoMock);
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldReturnForbidden403() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isForbidden());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"nam", "namenamenamename"}) // <4 and >15 characters
		@WithMockUser(authorities = {"ADMIN"})
		void whenNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, name, "password", "roleName");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"pas", "passwordpassword"}) // <4 and >15 characters
		@WithMockUser(authorities = {"ADMIN"})
		void whenPasswordSizeNotCorrect_shouldReturnBadRequest(String password) throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", password, "roleName");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"rol", "roleNameroleNamer"}) // <4 and >16 characters
		@WithMockUser(authorities = {"ADMIN"})
		void whenRoleNameSizeNotCorrect_shouldReturnBadRequest(String roleName) throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", roleName);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
		}
	}
	
	@Nested
	class Read {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(get(API_PATH)).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenAuthed_shouldReturnAppUserDto() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			MvcResult mvcResult = mockMvc.perform(get(API_PATH)).andExpect(status().isOk()).andReturn();
			
			AppUserDto actualAppUserDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AppUserDto.class);
			
			assertEquals(appUserDtoMock, actualAppUserDto);
		}		
	}
	
	@Nested
	class ReadMany {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(get(API_PATH + "/all")).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldReturnForbidden() throws Exception {
			mockMvc.perform(get(API_PATH + "/all")).andExpect(status().isForbidden());
		}
		
		@Test
		@WithMockUser(authorities = {"ADMIN"})
		void whenAuthedWithAdmin_shouldReturnAppUserDtoList() throws Exception {
			AppUserDto appUserDto1Mock = new AppUserDto(1, "name", "password", "roleName");
			AppUserDto appUserDto2Mock = new AppUserDto(2, "name", "password", "roleName");
			List<AppUserDto> appUserDtosMock = List.of(appUserDto1Mock, appUserDto2Mock);
			
			when(appUserCrudService.readMany()).thenReturn(appUserDtosMock);
			
			MvcResult mvcResult = mockMvc.perform(get(API_PATH + "/all")).andExpect(status().isOk()).andReturn();
			
			List<AppUserDto> actualAppUserDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<AppUserDto>>() {});
			
			assertEquals(appUserDtosMock.size(), actualAppUserDtos.size());
			assertEquals(appUserDtosMock.get(0), actualAppUserDtos.get(0));
			assertEquals(appUserDtosMock.get(1), actualAppUserDtos.get(1));
		}
	}
	
	@Nested
	class Update {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(put(API_PATH + "/1")).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenPathVariableNotInteger_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(put(API_PATH + "/abc")).andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"nam", "namenamenamename"}) // <4 and >15 characters
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, name, "password", "roleName");
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"pas", "passwordpassword"}) // <4 and >15 characters
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenPasswordSizeNotCorrect_shouldReturnBadRequest(String password) throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", password, "roleName");
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"rol", "roleNameroleNamer"}) // <4 and >16 characters
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenRoleNameSizeNotCorrect_shouldReturnBadRequest(String roleName) throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", roleName);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenAppUserIdDoesntMatch_shouldReturnBadRequest() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(2, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isBadRequest());
			
			verify(appUserCrudService, times(0)).update(anyInt(), any(AppUserDto.class));
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(1, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(appUserDtoMock)))
			.andExpect(status().isOk());
			
			verify(appUserCrudService).update(1, appUserDtoMock);
		}
	}
	
	@Nested
	class Delete {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"ADMIN"})
		void whenPathVariableNotInteger_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(delete(API_PATH + "/abc")).andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER"})
		void whenAuthedWithUser_shouldReturnForbidden() throws Exception {
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isForbidden());
		}
		
		@Test
		@WithMockUser(authorities = {"ADMIN"})
		void whenAuthedWithAdmin_shouldCallDelete() throws Exception {
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isOk());
			
			verify(appUserCrudService).delete(1);
		}
	}
}
