package app.fitbuddy.controller.operation;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.fitbuddy.FitBuddyApplication;
import app.fitbuddy.config.SecurityConfig;
import app.fitbuddy.dto.RegisterDTO;
import app.fitbuddy.service.operation.NewUserService;
import app.fitbuddy.service.operation.RegisterService;

@WebMvcTest(RegisterController.class)
@ContextConfiguration(classes = {FitBuddyApplication.class, SecurityConfig.class})
class RegisterControllerTest {
	
	@Autowired	MockMvc mockMvc;
	@Autowired	ObjectMapper objectMapper;
	@MockBean	RegisterService registerService;
	@MockBean	NewUserService newUserService;
	
	@Test
	void register_whenInputIsCorrect_shouldReturnOk() throws Exception {
		RegisterDTO registerDtoMock = new RegisterDTO("name", "password");
		
		when(registerService.register(anyString(), anyString())).thenReturn(1);
		
		mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDtoMock)))		
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		verify(registerService).register(anyString(), anyString());
		verify(newUserService).addDefaultExercises(anyInt());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"nam", "namenamenamename"}) // 3 and 16 characters
	void register_whenNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
		RegisterDTO registerDtoMock = new RegisterDTO(name, "password");
		
		mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDtoMock)))		
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"pas", "passwordpassword"}) // 3 and 16 characters
	void register_whenPasswordSizeNotCorrect_shouldReturnBadRequest(String password) throws Exception {
		RegisterDTO registerDtoMock = new RegisterDTO("name", password);
		
		mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerDtoMock)))		
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
