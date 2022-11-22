package app.fitbuddy.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.exercise.ExerciseRequestDTO;
import app.fitbuddy.dto.exercise.ExerciseResponseDTO;
import app.fitbuddy.dto.exercise.ExerciseUpdateDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Exercise;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.AppUserRepository;
import app.fitbuddy.testhelper.AppUserTestHelper;
import app.fitbuddy.testhelper.ExerciseTestHelper;

@ExtendWith(MockitoExtension.class)
class ExerciseMapperServiceTest {
	
	@InjectMocks
	ExerciseMapperService exerciseMapperService;
	
	@Mock
	AppUserRepository appUserRepository;
	
	@Nested
	class RequestDtoToEntity {
		
		@Test
		void requestDTOIsNull_returnNull() {
			Exercise actualExercise = exerciseMapperService.requestDtoToEntity(null);
			
			assertNull(actualExercise);			
		}
		
		@Test
		void appUserNotFound_throwFitBuddyException() {
			ExerciseRequestDTO requestDTO = new ExerciseRequestDTO("name", 1);
			
			when(appUserRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertThrows(FitBuddyException.class, () -> exerciseMapperService.requestDtoToEntity(requestDTO));
		}
		
		@Test
		void returnExercise() {
			ExerciseRequestDTO requestDTO = new ExerciseRequestDTO("name", 1);
			AppUser appUser = AppUserTestHelper.getMockAppUser(1, "name", "password");
			
			when(appUserRepository.findById(anyInt())).thenReturn(Optional.of(appUser));
			
			Exercise actualExercise = exerciseMapperService.requestDtoToEntity(requestDTO);
			
			assertEquals(requestDTO.getName(), actualExercise.getName());
			assertEquals(requestDTO.getAppUserId(), actualExercise.getAppUser().getId());
		}		
	}
	
	@Nested
	class EntityToResponseDto {
		
		@Test
		void entityIsNull_returnNull() {
			ExerciseResponseDTO actualResponseDTO = exerciseMapperService.entityToResponseDto(null);
			
			assertNull(actualResponseDTO);		
		}
		
		@Test
		void returnResponseDTO() {
			AppUser appUser = AppUserTestHelper.getMockAppUser(1, "name", "password");
			Exercise exercise = ExerciseTestHelper.getMockExercise(11, "exerciseName", appUser);
			
			ExerciseResponseDTO actualResponseDTO = exerciseMapperService.entityToResponseDto(exercise);
			
			assertEquals(exercise.getId(), actualResponseDTO.getId());
			assertEquals(exercise.getName(), actualResponseDTO.getName());
			assertEquals(exercise.getAppUser().getId(), actualResponseDTO.getAppUserId());
		}		
	}
	
	@Nested
	class EntitiesToResponseDtos {
		
		@Test
		void entitiesIsNull_returnEmptyList() {
			List<ExerciseResponseDTO> actualResponseDTOs = exerciseMapperService.entitiesToResponseDtos(null);
			
			assertEquals(Collections.emptyList(), actualResponseDTOs);
		}
		
		@Test
		void entitiesIsEmpty_returnEmptyList() {
			List<ExerciseResponseDTO> actualResponseDTOs = exerciseMapperService.entitiesToResponseDtos(
					Collections.emptyList());
			
			assertEquals(Collections.emptyList(), actualResponseDTOs);
		}
		
		@Test
		void returnResponseDTOs() {
			AppUser appUser = AppUserTestHelper.getMockAppUser(1, "name", "password");
			Exercise exercise1 = ExerciseTestHelper.getMockExercise(11, "exerciseName", appUser);
			Exercise exercise2 = ExerciseTestHelper.getMockExercise(22, "exerciseName", appUser);
			List<Exercise> exercises = List.of(exercise1, exercise2);
			
			List<ExerciseResponseDTO> actualResponseDTOs = exerciseMapperService.entitiesToResponseDtos(exercises);
			
			assertEquals(exercises.size(), actualResponseDTOs.size());
			assertThat(ExerciseTestHelper.isEqual(exercises.get(0), actualResponseDTOs.get(0))).isTrue();
			assertThat(ExerciseTestHelper.isEqual(exercises.get(1), actualResponseDTOs.get(1))).isTrue();			
		}		
	}
	
	@Nested
	class ApplyUpdateDtoToEntity {
		
		@Test
		void entityIsNull_returnNull() {
			ExerciseUpdateDTO updateDTO = new ExerciseUpdateDTO("exerciseName");
			
			Exercise actualExercise = exerciseMapperService.applyUpdateDtoToEntity(null, updateDTO);
			
			assertNull(actualExercise);
		}
		
		@Test
		void updateDTOIsNull_returnNull() {
			Exercise exercise = ExerciseTestHelper.getMockExercise(11, "exerciseName");
			
			Exercise actualExercise = exerciseMapperService.applyUpdateDtoToEntity(exercise, null);
			
			assertNull(actualExercise);
		}
		
		@Test
		void returnUpdatedExercise() {
			Exercise exercise = ExerciseTestHelper.getMockExercise(11, "exerciseName");
			ExerciseUpdateDTO updateDTO = new ExerciseUpdateDTO("newExerciseName");
			
			Exercise actualExercise = exerciseMapperService.applyUpdateDtoToEntity(exercise, updateDTO);
			
			assertEquals(updateDTO.getName(), actualExercise.getName());			
		}
	}
}
