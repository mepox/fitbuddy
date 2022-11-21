package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryUpdateDTO;
import app.fitbuddy.entity.History;
import app.fitbuddy.repository.HistoryRepository;
import app.fitbuddy.service.mapper.HistoryMapperService;
import app.fitbuddy.testhelper.HistoryTestHelper;

@ExtendWith(MockitoExtension.class)
class HistoryCrudServiceTest {
	
	@InjectMocks
	HistoryCrudService historyCrudService;
	
	@Mock
	HistoryRepository historyRepository;
	
	@Mock
	HistoryMapperService historyMapperService;
	
	@Nested
	class Create {
		
		@Test
		void requestDTOIsNull_returnNull() {			
			assertNull(historyCrudService.create(null));
		}
		
		@Test
		void callSave() {
			HistoryRequestDTO requestDTO = new HistoryRequestDTO(11, "exerciseName", 22, 33, "2022-01-01");
			History history = HistoryTestHelper.getMockHistory();
			
			when(historyMapperService.requestDtoToEntity(requestDTO)).thenReturn(history);
			
			historyCrudService.create(requestDTO);
			
			verify(historyRepository).save(history);
		}
	}
	
	@Nested
	class ReadById {
		
		@Test
		void notFoundById_returnNull() {
			when(historyRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertNull(historyCrudService.readById(1));
		}		
	}
	
	@Nested
	class ReadMany {
		
		@Test
		void callHistoryRepository() {
			historyCrudService.readMany(1, "2022-01-01");
			
			verify(historyRepository).findAllByUserIdAndDate(1, "2022-01-01");
		}		
	}
	
	@Nested
	class Update {
		
		@Test
		void updateDTOIsNull_returnNull() {
			assertNull(historyCrudService.update(1, null));
		}
		
		@Test
		void existingHistoryNotFound_returnNull() {
			HistoryUpdateDTO updateDTO = new HistoryUpdateDTO(11, 22);
			
			when(historyRepository.findById(anyInt())).thenReturn(Optional.empty());
			
			assertNull(historyCrudService.update(1, updateDTO));
		}
		
		@Test
		void applyAndSave() {
			HistoryUpdateDTO updateDTO = new HistoryUpdateDTO(11, 22);
			History history = HistoryTestHelper.getMockHistory();
			
			when(historyRepository.findById(anyInt())).thenReturn(Optional.of(history));
			when(historyMapperService.applyUpdateDtoToEntity(any(History.class), 
					any(HistoryUpdateDTO.class))).thenReturn(history);
			
			historyCrudService.update(1, updateDTO);
			
			verify(historyMapperService).applyUpdateDtoToEntity(history, updateDTO);
			verify(historyRepository).save(history);
		}		
	}
	
	@Nested
	class Delete {
		
		@Test
		void callDeleteById() {
			historyCrudService.delete(111);
			
			verify(historyRepository).deleteById(111);
		}		
	}
}
