package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.repository.ExerciseRepository;
import app.fitbuddy.service.mapper.ExerciseMapperService;

@ExtendWith(MockitoExtension.class)
class ExerciseCrudServiceTest {
	
	@InjectMocks
	ExerciseCrudService exerciseCrudService;
	
	@Mock
	ExerciseRepository exerciseRepository;
	
	@Mock
	ExerciseMapperService exerciseMapperService;
	
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
