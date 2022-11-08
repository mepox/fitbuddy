package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.converter.ExerciseConverterService;
import com.laszlojanku.fitbuddy.testhelper.ExerciseTestHelper;

@ExtendWith(MockitoExtension.class)
class ExerciseCrudServiceTest {
	
	@InjectMocks	ExerciseCrudService instance;
	@Mock	ExerciseCrudRepository exerciseCrudRepository;
	@Mock	ExerciseConverterService exerciseConverterService; 
	
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
	
	@Test
	void update_whenIdIsNull_shouldReturnNull() {
		ExerciseDto actualExerciseDto = instance.update(null, new ExerciseDto(1, "exerciseName", 11));
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void update_whenExerciseDtoIsNull_shouldReturnNull() {
		Exercise exerciseMock = ExerciseTestHelper.getMockExercise(1, "exerciseName");
		ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
		
		when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.of(exerciseMock));
		when(exerciseConverterService.convertToDto(any(Exercise.class))).thenReturn(exerciseDtoMock);
		
		ExerciseDto actualExerciseDto = instance.update(1, null);
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void update_whenExistingExerciseIsNotFound_shouldReturnNull() {
		when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		ExerciseDto actualExerciseDto = instance.update(1, new ExerciseDto(1, "exerciseName", 11));
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void update_whenExistingExerciseIsFound_shouldReturnUpdatedExerciseDto() {
		Exercise exerciseMock = ExerciseTestHelper.getMockExercise(1, "exerciseName");
		ExerciseDto exerciseDto = spy(new ExerciseDto(1, "exerciseName", 11));
		
		when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.of(exerciseMock));
		when(exerciseConverterService.convertToDto(any(Exercise.class))).thenReturn(exerciseDto);
		
		instance.update(1, new ExerciseDto(1, "newExerciseName", 11));
		
		verify(exerciseCrudRepository).save(any());		
		verify(exerciseDto).setName("newExerciseName");
	}

}
