package app.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.jpa.entity.Exercise;
import app.fitbuddy.jpa.repository.ExerciseCrudRepository;
import app.fitbuddy.jpa.service.converter.ExerciseConverterService;
import app.fitbuddy.testhelper.ExerciseTestHelper;

@ExtendWith(MockitoExtension.class)
class ExerciseCrudServiceTest {
	
	@InjectMocks	ExerciseCrudService instance;
	@Mock	ExerciseCrudRepository exerciseCrudRepository;
	@Mock	ExerciseConverterService exerciseConverterService;
	
	@Nested
	class Create {
		
		@Test
		void whenExerciseDtoIsNull_shouldReturnNull() {
			ExerciseDto actualExerciseDto = instance.create(null);
			
			assertNull(actualExerciseDto);
		}
		
		@Test
		void whenExerciseNameAlreadyExists_shouldReturnNull() {
			Exercise exerciseMock = ExerciseTestHelper.getMockExercise();
			
			when(exerciseCrudRepository.findByName(anyString())).thenReturn(Optional.of(exerciseMock));
			
			ExerciseDto actualExerciseDto = instance.create(new ExerciseDto(1, "exerciseName", 11));
			
			assertNull(actualExerciseDto);
		}
		
		@Test
		void whenCorrectInput_shouldCallSave() {
			ExerciseDto exerciseDtoSpy = spy(new ExerciseDto(1, "exerciseName", 11));
			
			when(exerciseCrudRepository.findByName(anyString())).thenReturn(Optional.empty());
			
			instance.create(exerciseDtoSpy);
			
			verify(exerciseDtoSpy).setId(null);
			verify(exerciseCrudRepository).save(any());
		}
	}
	
	@Nested
	class Update {
		
		@Test
		void update_whenIdIsNull_shouldReturnNull() {
			ExerciseDto actualExerciseDto = instance.update(null, Map.of("name", "exerciseName"));
			
			assertNull(actualExerciseDto);
		}
		
		@Test
		void update_whenMapIsNull_shouldReturnNull() {
			ExerciseDto actualExerciseDto = instance.update(1, null);
			
			assertNull(actualExerciseDto);
		}
		
		@Test
		void update_whenExistingExerciseIsNotFound_shouldReturnNull() {
			when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			ExerciseDto actualExerciseDto = instance.update(1, Map.of("name", "exerciseName"));
			
			assertNull(actualExerciseDto);
		}
		
		@Test
		void update_whenExistingExerciseIsFound_shouldReturnUpdatedExerciseDto() {
			Exercise exerciseMock = ExerciseTestHelper.getMockExercise(1, "exerciseName");
			ExerciseDto exerciseDto = spy(new ExerciseDto(1, "exerciseName", 11));
			
			when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.of(exerciseMock));
			when(exerciseConverterService.convertToDto(any(Exercise.class))).thenReturn(exerciseDto);
			
			instance.update(1, Map.of("name", "newExerciseName"));			
			
			verify(exerciseCrudRepository).save(any());		
			verify(exerciseDto).setName("newExerciseName");
		}
	}
	
	@Nested
	class ReadMany {
		
		@Test
		void readMany_whenUserIdIsNull_shouldReturnEmptyList() {
			List<ExerciseDto> actualExerciseDtos = instance.readMany(null);
			
			assertEquals(0, actualExerciseDtos.size());
		}
		
		@Test
		void readMany_whenNoExercisesFound_shouldReturnEmptyList() {
			when(exerciseCrudRepository.findAllByUserId(anyInt())).thenReturn(Collections.emptyList());
			
			List<ExerciseDto> actualExerciseDtos = instance.readMany(1);
			
			assertEquals(0, actualExerciseDtos.size());
		}
		
		@Test
		void readMany_whenExercisesFound_shouldReturnListOfExerciseDtos() {
			List<Exercise> exercisesMock = new ArrayList<>();		
			Exercise exerciseMock = ExerciseTestHelper.getMockExercise(1, "exerciseName");
			exercisesMock.add(exerciseMock);
			
			List<ExerciseDto> exerciseDtosMock = new ArrayList<>();
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
			exerciseDtosMock.add(exerciseDtoMock);		
			
			when(exerciseCrudRepository.findAllByUserId(anyInt())).thenReturn(exercisesMock);
			when(exerciseConverterService.convertAllEntity(any())).thenReturn(exerciseDtosMock);
			
			List<ExerciseDto> actualExerciseDtos = instance.readMany(1);
			
			assertEquals(exercisesMock.size(), actualExerciseDtos.size());
			assertEquals(exercisesMock.get(0).getName(), actualExerciseDtos.get(0).getName());
		}		
	}
}
