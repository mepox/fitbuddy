package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.exercise.ExerciseRequestDTO;
import app.fitbuddy.dto.exercise.ExerciseUpdateDTO;
import app.fitbuddy.entity.Exercise;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.ExerciseRepository;
import app.fitbuddy.service.mapper.ExerciseMapperService;
import app.fitbuddy.testhelper.ExerciseTestHelper;

@ExtendWith(MockitoExtension.class)
class ExerciseCrudServiceTest {
	
	@InjectMocks
	ExerciseCrudService exerciseCrudService;
	
	@Mock
	ExerciseRepository exerciseRepository;
	
	@Mock
	ExerciseMapperService exerciseMapperService;
	
	@Nested
	class Create {
		
		@Test
		void requestDTOIsNull_returnNull() {
			assertNull(exerciseCrudService.create(null));			
		}
		
		@Test
		void nameAlreadyExists_throwFitBuddyException() {
			ExerciseRequestDTO requestDTO = new ExerciseRequestDTO("exerciseName", 11);
			Exercise exercise = ExerciseTestHelper.getMockExercise();
			
			when(exerciseRepository.findByNameAndUserId(anyString(), anyInt())).thenReturn(Optional.of(exercise));
			
			assertThrows(FitBuddyException.class, () -> exerciseCrudService.create(requestDTO));
		}
		
		@Test
		void callSave() {
			ExerciseRequestDTO requestDTO = new ExerciseRequestDTO("exerciseName", 11);
			Exercise exercise = ExerciseTestHelper.getMockExercise();
			
			when(exerciseRepository.findByNameAndUserId(anyString(), anyInt())).thenReturn(Optional.empty());
			when(exerciseMapperService.requestDtoToEntity(requestDTO)).thenReturn(exercise);
			
			exerciseCrudService.create(requestDTO);
			
			verify(exerciseRepository).save(exercise);
		}		
	}
	
	@Nested
	class ReadById {
		
		@Test
		void notFoundById_returnNull() {
			when(exerciseRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertNull(exerciseCrudService.readById(1));			
		}		
	}
	
	@Nested
	class ReadMany {
		
		@Test
		void callExerciseRepository() {
			exerciseCrudService.readMany(11);
			
			verify(exerciseRepository).findAllByUserId(11);
		}		
	}
	
	@Nested
	class Update {
		
		@Test
		void updateDTOIsNull_returnNull() {
			assertNull(exerciseCrudService.update(1, null));
		}
		
		@Test
		void existingExerciseNotFound_returnNull() {
			ExerciseUpdateDTO updateDTO = new ExerciseUpdateDTO("newExerciseName");
			
			when(exerciseRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertNull(exerciseCrudService.update(1, updateDTO));			
		}
		
		@Test
		void nameAlreadyExists_throwFitBuddyException() {
			ExerciseUpdateDTO updateDTO = new ExerciseUpdateDTO("newExerciseName");
			Exercise exercise = ExerciseTestHelper.getMockExercise();
		
			when(exerciseRepository.findById(anyInt())).thenReturn(Optional.of(exercise));
			when(exerciseRepository.findByName(anyString())).thenReturn(Optional.of(exercise));
			
			assertThrows(FitBuddyException.class, () -> exerciseCrudService.update(1, updateDTO));
		}
		
		@Test
		void applyAndSave() {
			ExerciseUpdateDTO updateDTO = new ExerciseUpdateDTO("newExerciseName");
			Exercise exercise = ExerciseTestHelper.getMockExercise();
			
			when(exerciseRepository.findById(anyInt())).thenReturn(Optional.of(exercise));
			when(exerciseRepository.findByName(anyString())).thenReturn(Optional.empty());
			when(exerciseMapperService.applyUpdateDtoToEntity(any(Exercise.class), 
					any(ExerciseUpdateDTO.class))).thenReturn(exercise);
			
			exerciseCrudService.update(1, updateDTO);
			
			verify(exerciseMapperService).applyUpdateDtoToEntity(exercise, updateDTO);
			verify(exerciseRepository).save(exercise);
		}		
	}
	
	@Nested
	class Delete {
		
		@Test
		void callDeleteById() {
			exerciseCrudService.delete(111);
			
			verify(exerciseRepository).deleteById(111);			
		}		
	}
}
