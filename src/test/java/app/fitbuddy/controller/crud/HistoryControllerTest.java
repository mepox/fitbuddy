package app.fitbuddy.controller.crud;

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
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryResponseDTO;
import app.fitbuddy.dto.history.HistoryUpdateDTO;
import app.fitbuddy.service.crud.AppUserCrudService;
import app.fitbuddy.service.crud.HistoryCrudService;

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
			HistoryRequestDTO requestDTO = new HistoryRequestDTO(0, "exerciseName", 11, 111, "2022-01-01"); 
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))			
			.andExpect(status().is(302));			
		}		
		
		@ParameterizedTest
		@ValueSource(strings = {"abc", "1-1-2022", "01-01-2022", "2022-1-1", "2022-13-01", "2022-01-32"})
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenDateIsNotCorrect_shouldReturnBadRequest(String strDate) throws Exception {
			HistoryRequestDTO requestDTO = new HistoryRequestDTO(0, "exerciseName", 11, 111, strDate);
			AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(11, "name", "password", "roleName");

			when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);

			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestDTO)))
			.andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			HistoryRequestDTO historyRequestDTO = new HistoryRequestDTO(0, "exerciseName", 11, 111, "2022-01-01"); 
			AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(11, "name", "password", "roleName");
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);
			
			mockMvc.perform(post(API_PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyRequestDTO)))
			.andExpect(status().isOk());
					
			verify(historyCrudService).create(any(HistoryRequestDTO.class));		
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
			AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(11, "name", "password", "roleName");
			HistoryResponseDTO historyResponseDTO_1 = new HistoryResponseDTO(1, 11, "exerciseName1",
																			111, 1111, "2022-01-01");
			HistoryResponseDTO historyResponseDTO_2 = new HistoryResponseDTO(1, 11, "exerciseName2",
																			111, 1111, "2022-01-01");
			List<HistoryResponseDTO> historyResponseDTOs = List.of(historyResponseDTO_1, historyResponseDTO_2);
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);
			when(historyCrudService.readMany(anyInt(), anyString())).thenReturn(historyResponseDTOs);
			
			MvcResult mvcResult = mockMvc.perform(get(API_PATH + "/2022-01-01")).andExpect(status().isOk()).andReturn();
			
			List<HistoryResponseDTO> actualHistoryResponseDTOs = objectMapper.readValue(
					mvcResult.getResponse().getContentAsString(),
					new TypeReference<List<HistoryResponseDTO>>() {});
			
			assertEquals(historyResponseDTOs.size(), actualHistoryResponseDTOs.size());
			assertEquals(historyResponseDTOs.get(0), actualHistoryResponseDTOs.get(0));
			assertEquals(historyResponseDTOs.get(1), actualHistoryResponseDTOs.get(1));
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"abc", "1-1-2022", "01-01-2022", "2022-1-1", "2022-13-01", "2022-01-32"})
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
			HistoryUpdateDTO historyUpdateDTO = new HistoryUpdateDTO(11, 111);
			
			mockMvc.perform(put(API_PATH + "/abc")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyUpdateDTO)))
			.andExpect(status().isBadRequest());
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			HistoryUpdateDTO historyUpdateDTO = new HistoryUpdateDTO(11, 111);
			AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(11, "name", "password", "roleName");			
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);
			
			mockMvc.perform(put(API_PATH + "/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(historyUpdateDTO)))
			.andExpect(status().isOk());
			
			verify(historyCrudService).update(1, historyUpdateDTO);	
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
			HistoryResponseDTO historyResponseDTO = new HistoryResponseDTO(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(22, "name", "password", "roleName");			
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);
			when(historyCrudService.readById(anyInt())).thenReturn(historyResponseDTO);
			
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isBadRequest());
			
			verify(historyCrudService, times(0)).delete(1);
		}
		
		@Test
		@WithMockUser(authorities = {"USER", "ADMIN"})
		void whenInputIsCorrect_shouldReturnOk() throws Exception {
			HistoryResponseDTO historyResponseDTO = new HistoryResponseDTO(1, 11, "exerciseName", 111, 1111, "2022-01-01");
			AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO(11, "name", "password", "roleName");			
			
			when(appUserCrudService.readByName(anyString())).thenReturn(appUserResponseDTO);
			when(historyCrudService.readById(anyInt())).thenReturn(historyResponseDTO);
			
			mockMvc.perform(delete(API_PATH + "/1")).andExpect(status().isOk());
			
			verify(historyCrudService).delete(1);
		}
	}
}
