package app.fitbuddy.controller.operation;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.fitbuddy.FitBuddyApplication;
import app.fitbuddy.config.SecurityConfig;
import app.fitbuddy.dto.LoginDTO;
import app.fitbuddy.service.operation.LoginService;

@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {FitBuddyApplication.class, SecurityConfig.class})
class LoginControllerTest {
	
	@Autowired	MockMvc mockMvc;
	@Autowired	ObjectMapper objectMapper;
	@MockBean	LoginService loginService;
	
	final String API_PATH = "/login/perform_login";
	
	@Test
	void login_whenInputIsCorrect_ShouldReturnOk() throws Exception {
		LoginDTO loginDtoMock = new LoginDTO("name", "password");
		
		mockMvc.perform(post(API_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDtoMock)))
		.andExpect(status().isOk());
		
		verify(loginService).login(loginDtoMock);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"nam", "namenamenamename"}) // 3 and 16 characters
	void login_whenNameSizeNotCorrect_shouldReturnBadRequest(String name) throws Exception {
		LoginDTO loginDtoMock = new LoginDTO(name, "password");
		
		mockMvc.perform(post(API_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDtoMock)))
		.andExpect(status().isBadRequest());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"pas", "passwordpassword"}) // 3 and 16 characters
	void login_whenPasswordSizeNotCorrect_shouldReturnBadRequest(String password) throws Exception {
		LoginDTO loginDtoMock = new LoginDTO("name", password);
		
		mockMvc.perform(post(API_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDtoMock)))
		.andExpect(status().isBadRequest());
	}
}
