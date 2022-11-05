package com.laszlojanku.fitbuddy.jpa.service.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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
	void update_whenExistingHistoryNotFound_shouldReturnNull() {		
		when(historyCrudRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		HistoryDto actualHistoryDto = instance.update(1, new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022"));
		
		assertNull(actualHistoryDto);
	}
	
	@Test
	void update_whenExistingHistoryFound_shouldReturnUpdatedHistoryDto() {
		History historyMock = HistoryTestHelper.getMockHistory();
		HistoryDto historyDtoMock = new HistoryDto(1, 11, "exerciseName", 111, 1111, "01-01-2022");
		
		when(historyCrudRepository.findById(anyInt())).thenReturn(Optional.of(historyMock));
		when(historyConverterService.convertToDto(any(History.class))).thenReturn(historyDtoMock);
		when(historyConverterService.convertToEntity(any(HistoryDto.class))).thenReturn(historyMock);
		
		HistoryDto actualHistoryDto = instance.update(1, new HistoryDto(1, 11, "newExerciseName", 222, 2222, "02-02-2022"));
		
		verify(historyCrudRepository).save(historyMock);
		assertEquals("newExerciseName", actualHistoryDto.getExerciseName());
		assertEquals(222, actualHistoryDto.getWeight());
		assertEquals(2222, actualHistoryDto.getReps());
		assertEquals("02-02-2022", actualHistoryDto.getCreatedOn());
	}

}
