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
import app.fitbuddy.dto.HistoryDto;
import app.fitbuddy.jpa.service.crud.AppUserCrudService;
import app.fitbuddy.jpa.service.crud.HistoryCrudService;
import app.fitbuddy.operation.controller.HistoryController;

@WebMvcTest(HistoryController.class)
@ContextConfiguration(classes = {FitBuddyApplication.class, SecurityConfig.class})
class HistoryControllerTest {
	
	@Autowired	MockMvc mockMvc;
	@Autowired	ObjectMapper objectMapper;
	@MockBean	HistoryCrudService historyCrudService;
	@MockBean	AppUserCrudService appUserCrudService;
	
	final String API_PATH = "/user/history";
	
	@Nested
	class Create {
		
		@Test
		@WithAnonymousUser
		void whenNotAuthed_shouldReturnRedirect302() throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyDtoMock)))			
			.andExpect(status().is(302));			
		}		
		
		@ParameterizedTest
		@ValueSource(strings = {"abc", "1-1-2022", "01-01-2022", "2022-1-1"})
		@WithMockUser(authorities = {"USER", "ADMIN"})		
		void whenDateIsNotCorrect_shouldReturnBadRequest(String strDate) throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, strDate);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyDtoMock)))			
			.andExpect(status().isBadRequest());
		}		
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyDtoMock)))
			.andExpect(status().isOk());
					
			verify(historyCrudService).create(any(HistoryDto.class));		
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
			HistoryDto historyDto1Mock = new HistoryDto(1, 11, "exerciseName1", 111, 1111, "2022-01-01");
			HistoryDto historyDto2Mock = new HistoryDto(1, 11, "exerciseName2", 111, 1111, "2022-01-01");
			List<HistoryDto> historyDtosMock = List.of(historyDto1Mock, historyDto2Mock);
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			when(historyCrudService.readMany(anyInt(), anyString())).thenReturn(historyDtosMock);
			
			MvcResult mvcResult = mockMvc.perform(get(API_PATH + "/2022-01-01")).andExpect(status().isOk()).andReturn();
			
			List<HistoryDto> actualHistoryDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<HistoryDto>>() {});
			
			assertEquals(historyDtosMock.size(), actualHistoryDtos.size());
			assertEquals(historyDtosMock.get(0), actualHistoryDtos.get(0));
			assertEquals(historyDtosMock.get(1), actualHistoryDtos.get(1));
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"abc", "1-1-2022", "01-01-2022", "2022-1-1"})
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenPathVariableNotCorrectDate_shouldReturnBadRequest(String strDate) throws Exception {
			mockMvc.perform(get(API_PATH + "/" + strDate)).andExpect(status().isBadRequest());
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
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			
			mockMvc.perform(put(API_PATH + "/abc")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyDtoMock)))
			.andExpect(status().isBadRequest());
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"abc", "1-1-2022", "01-01-2022", "2022-1-1"})
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenNewDateIsNotCorrect_shouldReturnBadRequest(String strDate) throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, strDate);
			
			mockMvc.perform(put(API_PATH + "/abc")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyDtoMock)))
			.andExpect(status().isBadRequest());			
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");			
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyDtoMock)))
			.andExpect(status().isOk());
			
			verify(historyCrudService).update(1, historyDtoMock);	
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
		void whenUserIdDoesntMatch_shouldReturnBadRequest() throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			AppUserDto appUserDtoMock = new AppUserDto(22, "name", "password", "roleName");			
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			when(historyCrudService.read(anyInt())).thenReturn(historyDtoMock);
			
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isBadRequest());
			
			verify(historyCrudService, times(0)).delete(1);
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			AppUserDto appUserDtoMock = new AppUserDto(11, "name", "password", "roleName");			
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserDtoMock);
			when(historyCrudService.read(anyInt())).thenReturn(historyDtoMock);
			
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isOk());
			
			verify(historyCrudService).delete(1);
		}
	}
}
