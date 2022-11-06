package com.laszlojanku.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.testhelper.AppUserTestHelper;
import com.laszlojanku.fitbuddy.testhelper.ExerciseTestHelper;

@ExtendWith(MockitoExtension.class)
class ExerciseConverterServiceTest {
	
	@InjectMocks	ExerciseConverterService instance;
	
	@Nested
	class ConvertToEntity {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			Exercise actualExercise = instance.convertToEntity(null);
			
			assertNull(actualExercise);
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectEntity() {
			ExerciseDto exerciseDtoMock = new ExerciseDto(1, "exerciseName", 11);
			
			Exercise actualExercise = instance.convertToEntity(exerciseDtoMock);
			
			assertTrue(ExerciseTestHelper.isEqual(exerciseDtoMock, actualExercise));
		}
	}
	
	@Nested
	class ConvertToDto {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			ExerciseDto actualExerciseDto = instance.convertToDto(null);
			
			assertNull(actualExerciseDto);
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDto() {
			AppUser appUserMock = AppUserTestHelper.getMockAppUser(11, "name", "password");			
			Exercise exerciseMock = ExerciseTestHelper.getMockExercise(1, "exerciseName", appUserMock);
			
			ExerciseDto actualExerciseDto = instance.convertToDto(exerciseMock);
			
			assertTrue(ExerciseTestHelper.isEqual(exerciseMock, actualExerciseDto));		
		}		
	}
	
	@Nested
	class ConvertAllEntity {
		
		@Test
		void whenInputIsNull_shouldReturnEmptyList() {
			List<ExerciseDto> actualExerciseDtos = instance.convertAllEntity(null);
			
			assertTrue(actualExerciseDtos.isEmpty());
		}	
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDtos() {
			AppUser appUser1Mock = AppUserTestHelper.getMockAppUser(11, "name1", "password");			
			AppUser appUser2Mock = AppUserTestHelper.getMockAppUser(22, "name2", "password");						
			Exercise exercise1Mock = ExerciseTestHelper.getMockExercise(1, "exerciseName1", appUser1Mock);
			Exercise exercise2Mock = ExerciseTestHelper.getMockExercise(2, "exerciseName2", appUser2Mock);
			List<Exercise> exercisesMock = List.of(exercise1Mock, exercise2Mock);
			
			List<ExerciseDto> actualExerciseDtos = instance.convertAllEntity(exercisesMock);
			
			assertEquals(exercisesMock.size(), actualExerciseDtos.size());
			assertTrue(ExerciseTestHelper.isEqual(exercisesMock.get(0), actualExerciseDtos.get(0)));
		}
	}
}
