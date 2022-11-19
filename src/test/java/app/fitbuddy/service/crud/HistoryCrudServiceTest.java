package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.repository.HistoryRepository;
import app.fitbuddy.service.mapper.HistoryMapperService;

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
		
	}
	
	@Nested
	class ReadById {
		
	}
	
	@Nested
	class ReadMany {
		
	}
	
	@Nested
	class Update {
		
	}
	
	@Nested
	class Delete {
		
	}
}
