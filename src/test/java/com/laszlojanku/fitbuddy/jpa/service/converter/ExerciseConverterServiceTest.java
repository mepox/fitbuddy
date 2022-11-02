package com.laszlojanku.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;

@ExtendWith(MockitoExtension.class)
class ExerciseConverterServiceTest {
	
	@InjectMocks	ExerciseConverterService instance;
	
	@Test
	void convertToEntity_shouldReturnCorrectEntity() {
		ExerciseDto exerciseDto = new ExerciseDto(1, "exerciseName", 11);
		
		Exercise actualExercise = instance.convertToEntity(exerciseDto);
		
		assertEquals(exerciseDto.getId(), actualExercise.getId());
		assertEquals(exerciseDto.getName(), actualExercise.getName());
		assertEquals(exerciseDto.getAppUserId(), actualExercise.getAppUser().getId());
	}
	
	@Test
	void convertToEntity_whenInputIsNull_shouldReturnNull() {
		Exercise actualExercise = instance.convertToEntity(null);
		
		assertNull(actualExercise);
	}
	
	@Test
	void convertToDto_shouldReturnCorrectDto() {
		AppUser appUser = new AppUser();
		appUser.setId(11);
		
		Exercise exercise = new Exercise();
		exercise.setId(1);
		exercise.setName("exerciseName");
		exercise.setAppUser(appUser);
		
		ExerciseDto actualExerciseDto = instance.convertToDto(exercise);
		
		assertEquals(exercise.getId(), actualExerciseDto.getId());
		assertEquals(exercise.getName(), actualExerciseDto.getName());
		assertEquals(exercise.getAppUser().getId(), actualExerciseDto.getAppUserId());		
	}
	
	@Test
	void convertToDto_whenInputIsNull_shouldReturnNull() {
		ExerciseDto actualExerciseDto = instance.convertToDto(null);
		
		assertNull(actualExerciseDto);
	}
	
	@Test
	void convertAllEntity_shouldReturnCorrectDtos() {
		AppUser appUser1 = new AppUser();
		appUser1.setId(11);
		AppUser appUser2 = new AppUser();
		appUser2.setId(22);
		
		Exercise exercise1 = new Exercise();
		exercise1.setId(1);
		exercise1.setName("exerciseName1");
		exercise1.setAppUser(appUser1);
		Exercise exercise2 = new Exercise();
		exercise2.setId(2);
		exercise2.setName("exerciseName2");
		exercise2.setAppUser(appUser2);		
		
		List<Exercise> exercises = Arrays.asList(exercise1, exercise2);
		
		List<ExerciseDto> actualExerciseDtos = instance.convertAllEntity(exercises);
		
		assertEquals(exercises.size(), actualExerciseDtos.size());
		assertEquals(exercises.get(0).getId(), actualExerciseDtos.get(0).getId());
		assertEquals(exercises.get(0).getName(), actualExerciseDtos.get(0).getName());
		assertEquals(exercises.get(0).getAppUser().getId(), actualExerciseDtos.get(0).getAppUserId());
		assertEquals(exercises.get(1).getId(), actualExerciseDtos.get(1).getId());
		assertEquals(exercises.get(1).getName(), actualExerciseDtos.get(1).getName());
		assertEquals(exercises.get(1).getAppUser().getId(), actualExerciseDtos.get(1).getAppUserId());		
	}
	
	@Test
	void convertAllEntity_whenInputIsNull_shouldReturnNull() {
		List<ExerciseDto> actualExerciseDtos = instance.convertAllEntity(null);
		
		assertNull(actualExerciseDtos);
	}

}
