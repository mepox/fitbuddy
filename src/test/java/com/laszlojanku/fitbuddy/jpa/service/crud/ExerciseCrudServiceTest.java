package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.converter.ExerciseConverterService;

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
		List<Exercise> exercises = new ArrayList<>();		
		Exercise exercise = getMockExercise(1, "exerciseName");
		exercises.add(exercise);
		
		List<ExerciseDto> exerciseDtos = new ArrayList<>();
		ExerciseDto exerciseDto = new ExerciseDto(1, "exerciseName", 11);
		exerciseDtos.add(exerciseDto);		
		
		when(exerciseCrudRepository.findAllByUserId(anyInt())).thenReturn(exercises);
		when(exerciseConverterService.convertAllEntity(any())).thenReturn(exerciseDtos);
		
		List<ExerciseDto> actualExerciseDtos = instance.readMany(1);
		
		assertEquals(exercises.size(), actualExerciseDtos.size());
		assertEquals(exercises.get(0).getName(), actualExerciseDtos.get(0).getName());
	}
	
	@Test
	void update_whenIdIsNull_shouldReturnNull() {
		ExerciseDto actualExerciseDto = instance.update(null, new ExerciseDto(1, "exerciseName", 11));
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void update_whenExerciseDtoIsNull_shouldReturnNull() {
		Exercise exercise = getMockExercise(1, "exerciseName");
		ExerciseDto exerciseDto = new ExerciseDto(1, "exerciseName", 11);
		
		when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.of(exercise));
		when(exerciseConverterService.convertToDto(any(Exercise.class))).thenReturn(exerciseDto);
		
		ExerciseDto actualExerciseDto = instance.update(1, null);
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void update_whenExerciseDtoIsNotFound_shouldReturnNull() {
		when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		ExerciseDto actualExerciseDto = instance.update(1, new ExerciseDto(1, "exerciseName", 11));
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void update_whenExerciseDtoIsFound_shouldReturnUpdatedExerciseDto() {
		Exercise exercise = getMockExercise(1, "exerciseName");
		ExerciseDto exerciseDto = new ExerciseDto(1, "exerciseName", 11);
		Exercise updatedExercise = getMockExercise(1, "newExerciseName");
		
		when(exerciseCrudRepository.findById(anyInt())).thenReturn(Optional.of(exercise));
		when(exerciseConverterService.convertToDto(any(Exercise.class))).thenReturn(exerciseDto);
		when(exerciseConverterService.convertToEntity(any(ExerciseDto.class))).thenReturn(updatedExercise);
		
		ExerciseDto actualExerciseDto = instance.update(1, new ExerciseDto(1, "newExerciseName", 11));
		
		verify(exerciseCrudRepository).save(updatedExercise);
		assertEquals("newExerciseName", actualExerciseDto.getName());		
	}
	
	// Helper methods
	
	private Exercise getMockExercise(Integer id, String exerciseName) {
		Role role = new Role();
		role.setId(111);
		role.setName("roleName");
		
		AppUser appUser = new AppUser();
		appUser.setId(11);
		appUser.setName("userName");
		appUser.setPassword("password");
		appUser.setRole(role);
		
		Exercise exercise = new Exercise();
		exercise.setId(id);
		exercise.setName(exerciseName);
		exercise.setAppUser(appUser);
		
		return exercise;		
	}

}
