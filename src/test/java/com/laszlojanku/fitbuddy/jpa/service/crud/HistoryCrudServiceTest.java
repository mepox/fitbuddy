package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.entity.History;
import com.laszlojanku.fitbuddy.jpa.repository.HistoryCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.converter.HistoryConverterService;
import com.laszlojanku.fitbuddy.testhelper.HistoryTestHelper;

@ExtendWith(MockitoExtension.class)
class HistoryCrudServiceTest {
	
	@InjectMocks	HistoryCrudService instance;
	@Mock	HistoryCrudRepository historyCrudRepository;
	@Mock	HistoryConverterService historyConverterService;
	
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
		List<History> histories = new ArrayList<>();
		History history = HistoryTestHelper.getMockHistory();
		histories.add(history);
		
		List<HistoryDto> historyDtos = new ArrayList<>();
		HistoryDto historyDto = new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022");
		historyDtos.add(historyDto);
		
		when(historyCrudRepository.findAllByUserIdAndDate(anyInt(), anyString())).thenReturn(histories);
		when(historyConverterService.convertAllEntity(any())).thenReturn(historyDtos);		
		
		List<HistoryDto> actualHistoryDtos = instance.readMany(1, "01-01-2022");
		
		assertEquals(historyDtos.size(), actualHistoryDtos.size());
	}
	
	@Test
	void update_whenIdIsNull_shouldReturnNull() {
		HistoryDto actualHistoryDto = instance.update(null, new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022"));
		
		assertNull(actualHistoryDto);		
	}
	
	@Test
	void update_whenHistoryDtoIsNull_shouldReturnNull() {
		HistoryDto actualHistoryDto = instance.update(1, null);
		
		assertNull(actualHistoryDto);
	}
	
	@Test
	void update_whenHistoryNotFound_shouldReturnNull() {		
		when(historyCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		HistoryDto actualHistoryDto = instance.update(1, new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022"));
		
		assertNull(actualHistoryDto);
	}
	
	@Test
	void update_whenHistoryFound_shouldReturnUpdatedHistoryDto() {
		History history = HistoryTestHelper.getMockHistory();
		HistoryDto historyDto = new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022");
		
		when(historyCrudRepository.findById(anyInt())).thenReturn(Optional.of(history));
		when(historyConverterService.convertToDto(any(History.class))).thenReturn(historyDto);
		
		HistoryDto actualHistoryDto = instance.update(1, new HistoryDto(1, 11, "newExerciseName", 222, 2222, "02-02-2022"));
		
		assertEquals("newExerciseName", actualHistoryDto.getExerciseName());
		assertEquals(222, actualHistoryDto.getWeight());
		assertEquals(2222, actualHistoryDto.getReps());
		assertEquals("02-02-2022", actualHistoryDto.getCreatedOn());
	}

}
