package app.fitbuddy.jpa.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.HistoryDto;
import app.fitbuddy.jpa.entity.History;
import app.fitbuddy.jpa.repository.ExerciseCrudRepository;
import app.fitbuddy.testhelper.AppUserTestHelper;
import app.fitbuddy.testhelper.ExerciseTestHelper;
import app.fitbuddy.testhelper.HistoryTestHelper;

@ExtendWith(MockitoExtension.class)
class HistoryConverterServiceTest {
	
	@InjectMocks	HistoryConverterService instance;
	@Mock	ExerciseCrudRepository exerciseCrudRepository;
	
	@Nested
	class ConvertToEntity {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			History actualHistory = instance.convertToEntity(null);
			
			assertNull(actualHistory);
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectEntity() {
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022");
			
			when(exerciseCrudRepository.findIdByNameAndUserId(anyString(), anyInt())).thenReturn(123);
			
			History actualHistory = instance.convertToEntity(historyDtoMock);
			
			assertTrue(HistoryTestHelper.isEqual(historyDtoMock, actualHistory));	
		}
	}
	
	@Nested
	class ConvertToDto {
		
		@Test
		void whenInputIsNull_shouldReturnNull() {
			HistoryDto actualHistoryDto = instance.convertToDto(null);
			
			assertNull(actualHistoryDto);
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDto() {
			History historyMock = HistoryTestHelper.getMockHistory();
			
			HistoryDto actualHistoryDto = instance.convertToDto(historyMock);
			
			assertTrue(HistoryTestHelper.isEqual(historyMock, actualHistoryDto));		
		}
	}
	
	@Nested
	class ConvertAllEntity {
		
		@Test
		void whenInputIsNull_shouldReturnEmptyList() {
			List<HistoryDto> actualHistoryDtos = instance.convertAllEntity(null);
			
			assertTrue(actualHistoryDtos.isEmpty());
		}
	
		@Test
		void whenInputIsCorrect_shouldReturnCorrectDtos() {
			History history1Mock = HistoryTestHelper.getMockHistory(1, 11, 111, "01-01-2022",
					ExerciseTestHelper.getMockExercise(1, "exerciseName1"), AppUserTestHelper.getMockAppUser());
			History history2Mock = HistoryTestHelper.getMockHistory(2, 22, 222, "02-02-2022",
					ExerciseTestHelper.getMockExercise(2, "exerciseName2"), AppUserTestHelper.getMockAppUser());
			List<History> historiesMock = List.of(history1Mock, history2Mock);
			
			List<HistoryDto> actualHistoryDtos = instance.convertAllEntity(historiesMock);
			
			assertEquals(historiesMock.size(), actualHistoryDtos.size());
			assertTrue(HistoryTestHelper.isEqual(historiesMock.get(0), actualHistoryDtos.get(0)));
			assertTrue(HistoryTestHelper.isEqual(historiesMock.get(1), actualHistoryDtos.get(1)));		
		}
	}
}
