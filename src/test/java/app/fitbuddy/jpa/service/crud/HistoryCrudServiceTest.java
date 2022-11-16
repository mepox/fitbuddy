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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.HistoryDto;
import app.fitbuddy.jpa.entity.History;
import app.fitbuddy.jpa.repository.HistoryCrudRepository;
import app.fitbuddy.jpa.service.converter.HistoryConverterService;
import app.fitbuddy.testhelper.HistoryTestHelper;

@ExtendWith(MockitoExtension.class)
class HistoryCrudServiceTest {
	
	@InjectMocks	HistoryCrudService instance;
	@Mock	HistoryCrudRepository historyCrudRepository;
	@Mock	HistoryConverterService historyConverterService;
	
	@Nested
	class Create {
		
		@Test
		void whenHistoryDtoIsNull_shouldReturnNull() {
			HistoryDto actualHistoryDto = instance.create(null);
			
			assertNull(actualHistoryDto);
		}
		
		@Test
		void whenCorrectInput_shouldCallSave() {
			HistoryDto historyDtoSpy = spy(new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022"));
			
			instance.create(historyDtoSpy);
			
			verify(historyDtoSpy).setId(null);
			verify(historyCrudRepository).save(any());
		}
	}
	
	@Nested
	class Update {
		
		@Test
		void update_whenIdIsNull_shouldReturnNull() {
			HistoryDto actualHistoryDto = instance.update(null, Map.of("weight", "11"));
			
			assertNull(actualHistoryDto);		
		}
		
		@Test
		void update_whenMapIsNull_shouldReturnNull() {
			HistoryDto actualHistoryDto = instance.update(1, null);
			
			assertNull(actualHistoryDto);
		}
		
		@Test
		void update_whenExistingHistoryNotFound_shouldReturnNull() {		
			when(historyCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			HistoryDto actualHistoryDto = instance.update(1, Map.of("weight", "11"));
			
			assertNull(actualHistoryDto);
		}
		
		@Test
		void update_whenExistingHistoryFound_shouldReturnUpdatedHistoryDto() {
			History historyMock = HistoryTestHelper.getMockHistory();
			HistoryDto historyDto = spy(new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022"));
			
			when(historyCrudRepository.findById(anyInt())).thenReturn(Optional.of(historyMock));
			when(historyConverterService.convertToDto(any(History.class))).thenReturn(historyDto);
			
			instance.update(1, Map.of(	"weight", "222",
										"reps", "2222"));
			
			verify(historyCrudRepository).save(any());		
			verify(historyDto).setWeight(222);
			verify(historyDto).setReps(2222);				
		}
	}
	
	@Nested
	class ReadMany {
		
		@Test
		void readMany_whenIdIsNull_shouldReturnEmptyList() {
			List<HistoryDto> actualHistoryDtos = instance.readMany(null, "01-01-2022");
			
			assertEquals(0, actualHistoryDtos.size());		
		}
		
		@Test
		void readMany_whenDateIsNull_shouldReturnEmptyList() {
			List<HistoryDto> actualHistoryDtos = instance.readMany(1, null);
			
			assertEquals(0, actualHistoryDtos.size());
		}
		
		@Test
		void readMany_whenHistoriesFound_shouldReturnListOfHistoryDto() {
			List<History> historiesMock = new ArrayList<>();
			History historyMock = HistoryTestHelper.getMockHistory();
			historiesMock.add(historyMock);
			
			List<HistoryDto> historyDtosMock = new ArrayList<>();
			HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022");
			historyDtosMock.add(historyDtoMock);
			
			when(historyCrudRepository.findAllByUserIdAndDate(anyInt(), anyString())).thenReturn(historiesMock);
			when(historyConverterService.convertAllEntity(any())).thenReturn(historyDtosMock);		
			
			List<HistoryDto> actualHistoryDtos = instance.readMany(1, "01-01-2022");
			
			assertEquals(historyDtosMock.size(), actualHistoryDtos.size());
		}
	}
}
