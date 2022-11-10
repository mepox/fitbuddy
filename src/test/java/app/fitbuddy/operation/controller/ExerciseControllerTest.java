package app.fitbuddy.operation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.jpa.service.crud.AppUserCrudService;
import app.fitbuddy.jpa.service.crud.ExerciseCrudService;

@WebMvcTest(ExerciseController.class)
@ContextConfiguration(classes = {FitBuddyApplication.class, SecurityConfig.class})
class ExerciseControllerTest {
	
	@Autowired	MockMvc mockMvc;
	@Autowired	ObjectMapper objectMapper;
	@MockBean	ExerciseCrudService exerciseCrudService;
	@MockBean	AppUserCrudService appUserCrudService;
	
	final String API_PATH = "/user/exercises";
	
	@Nested
	class Create {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(exerciseDtoMock)))
			.andExpect(status().is(302));
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"", "exerciseNameexerciseNameexerciseName"}) // <1 or >32
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenExerciseNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, name, 11);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(exerciseDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(exerciseDtoMock)))
			.andExpect(status().isOk());
					
			verify(exerciseCrudService).create(any(ExerciseDto.class));
		}		
	}
	
	@Nested
	class ReadAll {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(get(API_PATH)).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenAuthed_shouldReturnExerciseDtoList() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");
			ExerciseDto exerciseDto1Mock = new ExerciseDto(1, "exerciseName", 11);
			ExerciseDto exerciseDto2Mock = new ExerciseDto(2, "exerciseName", 11);
			List<ExerciseDto> exerciseDtosMock = List.of(exerciseDto1Mock, exerciseDto2Mock);
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			when(exerciseCrudService.readMany(anyInt())).thenReturn(exerciseDtosMock);
			
			MvcResult mvcResult = mockMvc.perform(get(API_PATH)).andExpect(status().isOk()).andReturn();
			
			List<ExerciseDto> actualExerciseDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<ExerciseDto>>() {});
			
			assertEquals(exerciseDtosMock.size(), actualExerciseDtos.size());
			assertEquals(exerciseDtosMock.get(0), actualExerciseDtos.get(0));
			assertEquals(exerciseDtosMock.get(1), actualExerciseDtos.get(1));
		}		
	}
	
	@Nested
	class Update {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(put(API_PATH)).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenPathVariableNotInteger_shouldReturnBadRequest() throws Exception {
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
			
			mockMvc.perform(put(API_PATH + "/abc")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(exerciseDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"", "exerciseNameexerciseNameexerciseName"}) // <1 or >32
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenExerciseNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, name, 11);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(exerciseDtoMock)))
			.andExpect(status().isBadRequest());
		}		
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "newExerciseName", 11);
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(exerciseDtoMock)))
			.andExpect(status().isOk());
			
			verify(exerciseCrudService).update(1, exerciseDtoMock);	
		}
	}
	
	@Nested
	class Delete {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			mockMvc.perform(delete(API_PATH)).andExpect(status().is(302));
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenPathVariableNotInteger_shouldReturnBadRequest() throws Exception {
			mockMvc.perform(delete(API_PATH + "/abc")).andExpect(status().isBadRequest());
		}		
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenAppUserIdDoesntMatch_shouldReturnBadRequest() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 22);
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			when(exerciseCrudService.read(anyInt())).thenReturn(exerciseDtoMock);
			
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isBadRequest());
			
			verify(exerciseCrudService, times(0)).delete(anyInt());
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			when(exerciseCrudService.read(anyInt())).thenReturn(exerciseDtoMock);
			
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isOk());
			
			verify(exerciseCrudService).delete(1);
		}
	}
}
