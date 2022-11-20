package app.fitbuddy.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryResponseDTO;
import app.fitbuddy.dto.history.HistoryUpdateDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Exercise;
import app.fitbuddy.entity.History;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.testhelper.AppUserTestHelper;
import app.fitbuddy.testhelper.ExerciseTestHelper;
import app.fitbuddy.testhelper.HistoryTestHelper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.repository.AppUserRepository;
import app.fitbuddy.repository.ExerciseRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class HistoryMapperServiceTest {
	
	@InjectMocks
	HistoryMapperService historyMapperService;
	
	@Mock
	AppUserRepository appUserRepository;
	
	@Mock
	ExerciseRepository exerciseRepository;
	
	@Nested
	class RequestDtoToEntity {

		@Test
		void requestDTOIsNull_returnNull() {
			History actualHistory = historyMapperService.requestDtoToEntity(null);

			assertNull(actualHistory);
		}

		@Test
		void appUserNotFound_throwFitBuddyException() {
			HistoryRequestDTO requestDTO = new HistoryRequestDTO(1, "exerciseName", 11, 111, "2022-01-01");

			when(appUserRepository.findById(anyInt())).thenReturn(Optional.empty());

			assertThrows(FitBuddyException.class, () -> historyMapperService.requestDtoToEntity(requestDTO));
		}

		@Test
		void exerciseNotFound_throwFitBuddyException() {
			HistoryRequestDTO requestDTO = new HistoryRequestDTO(1, "exerciseName", 11, 111, "2022-01-01");
			AppUser appUser = AppUserTestHelper.getMockAppUser();

			when(appUserRepository.findById(anyInt())).thenReturn(Optional.of(appUser));
			when(exerciseRepository.findByNameAndUserId(anyString(), anyInt())).thenReturn(Optional.empty());

			assertThrows(FitBuddyException.class, () -> historyMapperService.requestDtoToEntity(requestDTO));
		}

		@Test
		void returnHistory() {
			HistoryRequestDTO requestDTO = new HistoryRequestDTO(1, "exerciseName", 11, 111, "2022-01-01");
			AppUser appUser = AppUserTestHelper.getMockAppUser();
			Exercise exercise = ExerciseTestHelper.getMockExercise();

			when(appUserRepository.findById(anyInt())).thenReturn(Optional.of(appUser));
			when(exerciseRepository.findByNameAndUserId(anyString(), anyInt())).thenReturn(Optional.of(exercise));

			History actualHistory = historyMapperService.requestDtoToEntity(requestDTO);

			assertEquals(requestDTO.getAppUserId(), actualHistory.getAppUser().getId());
			assertEquals(requestDTO.getExerciseName(), actualHistory.getExercise().getName());
			assertEquals(requestDTO.getWeight(), actualHistory.getWeight());
			assertEquals(requestDTO.getReps(), actualHistory.getReps());
			assertEquals(requestDTO.getCreatedOn(), actualHistory.getCreatedOn());
		}
	}
	
	@Nested
	class EntityToResponseDto {

		@Test
		void entityIsNull_returnNull() {
			HistoryResponseDTO actualResponseDTO = historyMapperService.entityToResponseDto(null);

			assertNull(actualResponseDTO);
		}

		@Test
		void returnResponseDTO() {
			Exercise exercise = ExerciseTestHelper.getMockExercise(1, "exerciseName");
			AppUser appUser = AppUserTestHelper.getMockAppUser(11, "name", "password");
			History history = HistoryTestHelper.getMockHistory(111, 22, 222, "2022-01-01", exercise, appUser);
			
			HistoryResponseDTO actualResponseDTO = historyMapperService.entityToResponseDto(history);
			
			assertEquals(history.getId(), actualResponseDTO.getId());
			assertEquals(history.getWeight(), actualResponseDTO.getWeight());
			assertEquals(history.getReps(), actualResponseDTO.getReps());
			assertEquals(history.getCreatedOn(), actualResponseDTO.getCreatedOn());
			assertEquals(history.getExercise().getName(), actualResponseDTO.getExerciseName());
			assertEquals(history.getAppUser().getId(), actualResponseDTO.getAppUserId());
		}
	}
	
	@Nested
	class EntitiesToResponseDtos {
		
		@Test
		void entitiesIsNull_returnEmptyList() {
			List<HistoryResponseDTO> actualResponseDTOs = historyMapperService.entitiesToResponseDtos(null);
			
			assertEquals(Collections.emptyList(), actualResponseDTOs);
		}
		
		@Test
		void entitiesIsEmpty_returnEmptyList() {
			List<HistoryResponseDTO> actualResponseDTOs = historyMapperService.entitiesToResponseDtos(
					Collections.emptyList());
			
			assertEquals(Collections.emptyList(), actualResponseDTOs);			
		}
		
		@Test
		void returnResponseDTOs() {
			Exercise exercise = ExerciseTestHelper.getMockExercise(1, "exerciseName");
			AppUser appUser = AppUserTestHelper.getMockAppUser(11, "name", "password");
			History history1 = HistoryTestHelper.getMockHistory(111, 22, 222, "2022-01-01", exercise, appUser);
			History history2 = HistoryTestHelper.getMockHistory(222, 22, 222, "2022-01-01", exercise, appUser);
			List<History> histories = List.of(history1, history2);
			
			List<HistoryResponseDTO> responseDTOs = historyMapperService.entitiesToResponseDtos(histories);
			
			assertEquals(histories.size(), responseDTOs.size());
			assertThat(HistoryTestHelper.isEqual(histories.get(0), responseDTOs.get(0))).isTrue();
			assertThat(HistoryTestHelper.isEqual(histories.get(1), responseDTOs.get(1))).isTrue();
		}
	}
	
	@Nested
	class ApplyUpdateDtoToEntity {
		
		@Test
		void entityIsNull_returnNull() {
			HistoryUpdateDTO updateDTO = new HistoryUpdateDTO(1, 2);
			
			History actualHistory = historyMapperService.applyUpdateDtoToEntity(null, updateDTO);
			
			assertNull(actualHistory);
		}
		
		@Test
		void updateDTOIsNull_returnNull() {
			History history = HistoryTestHelper.getMockHistory(1, 1, 2, "2022-01-01");
			
			History actualHistory = historyMapperService.applyUpdateDtoToEntity(history, null);
			
			assertNull(actualHistory);			
		}
		
		@Test
		void returnUpdatedHistory() {
			History history = HistoryTestHelper.getMockHistory(1, 1, 2, "2022-01-01");
			HistoryUpdateDTO updateDTO = new HistoryUpdateDTO(3, 4);
			
			History actualHistory = historyMapperService.applyUpdateDtoToEntity(history, updateDTO);
			
			assertEquals(updateDTO.getWeight(), actualHistory.getWeight());
			assertEquals(updateDTO.getReps(), actualHistory.getReps());			
		}		
	}
}
