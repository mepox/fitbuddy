package com.laszlojanku.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.entity.History;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseCrudRepository;

@ExtendWith(MockitoExtension.class)
class HistoryConverterServiceTest {
	
	@InjectMocks	HistoryConverterService instance;
	@Mock	ExerciseCrudRepository exerciseCrudRepository;
	
	@Test
	void convertToEntity_whenInputIsCorrect_shouldReturnCorrectEntity() {
		HistoryDto historyDto = new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022");
		
		when(exerciseCrudRepository.findIdByNameAndUserId(anyString(), anyInt())).thenReturn(123);
		
		History actualHistory = instance.convertToEntity(historyDto);
		
		assertTrue(isEqual(actualHistory, historyDto));
	}
	
	@Test
	void convertToEntity_whenInputIsNull_shouldReturnNull() {
		History actualHistory = instance.convertToEntity(null);
		
		assertNull(actualHistory);
	}
	
	@Test
	void convertToDto_whenInputIsCorrect_shouldReturnCorrectDto() {
		History history = getMockHistory(1, 11, 123, "exerciseName", 111, 1111, "01-01-2022");
		
		HistoryDto actualHistoryDto = instance.convertToDto(history);
		
		assertTrue(isEqual(history, actualHistoryDto));		
	}
	
	@Test
	void convertToDto_whenInputIsNull_shouldReturnNull() {
		HistoryDto actualHistoryDto = instance.convertToDto(null);
		
		assertNull(actualHistoryDto);
	}
	
	@Test
	void convertAllEntity_whenInputIsCorrect_shouldReturnCorrectDtos() {
		History history1 = getMockHistory(1, 11, 123, "exerciseName1", 111, 1111, "01-01-2022");
		History history2 = getMockHistory(2, 22, 321, "exerciseName2", 222, 2222, "02-02-2022");
		List<History> histories = Arrays.asList(history1, history2);
		
		List<HistoryDto> actualHistoryDtos = instance.convertAllEntity(histories);
		
		assertEquals(histories.size(), actualHistoryDtos.size());
		assertTrue(isEqual(histories.get(0), actualHistoryDtos.get(0)));
		assertTrue(isEqual(histories.get(1), actualHistoryDtos.get(1)));
		
	}
	
	@Test
	void convertAllEntity_whenInputIsNull_shouldReturnNull() {
		List<HistoryDto> actualHistoryDtos = instance.convertAllEntity(null);
		
		assertNull(actualHistoryDtos);
	}
	
	// Helper methods
	
	private History getMockHistory(Integer id, Integer appUserId, Integer exerciseId, String exerciseName, Integer weight, Integer reps, String createdOn) {
		AppUser appUser = new AppUser();
		appUser.setId(appUserId);
		
		Exercise exercise = new Exercise();
		exercise.setId(exerciseId);
		exercise.setName(exerciseName);		
		
		History history = new History();
		history.setId(id);
		history.setAppUser(appUser);
		history.setExercise(exercise);
		history.setWeight(weight);
		history.setReps(reps);
		history.setCreatedOn(createdOn);
		
		return history;
	}
	
	private boolean isEqual(History history, HistoryDto historyDto) {
		return (history.getId().equals(historyDto.getId()) && 
				history.getAppUser().getId().equals(historyDto.getAppUserId()) &&				
				history.getExercise().getName().equals(historyDto.getExerciseName()) &&				
				history.getWeight().equals(historyDto.getWeight()) &&
				history.getReps().equals(historyDto.getReps()) &&
				history.getCreatedOn().equals(historyDto.getCreatedOn()));
	}	

}
