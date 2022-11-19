package app.fitbuddy.service.crud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.fitbuddy.repository.RoleRepository;
import app.fitbuddy.service.mapper.RoleMapperService;

@ExtendWith(MockitoExtension.class)
class RoleCrudServiceTest {
	
	@InjectMocks
	RoleCrudService roleCrudService;
	
	@Mock
	RoleRepository roleRepository;
	
	@Mock
	RoleMapperService roleMapperService;
	
	@Nested
	class Create {
		
	}
	
	@Nested
	class ReadById {
		
	}
	
	@Nested
	class ReadByName {
		
	}
	
	@Nested
	class ReadAll {
		
	}
	
	@Nested
	class Update {
		
	}
	
	@Nested
	class Delete {
		
	}
}
