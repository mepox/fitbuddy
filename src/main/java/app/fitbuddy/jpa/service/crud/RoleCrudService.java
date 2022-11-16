package app.fitbuddy.jpa.service.crud;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.RoleDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.Role;
import app.fitbuddy.jpa.repository.RoleCrudRepository;
import app.fitbuddy.jpa.service.GenericCrudService;
import app.fitbuddy.jpa.service.converter.RoleConverterService;

@Service
public class RoleCrudService extends GenericCrudService<RoleDto, Role> {
	
	private final RoleCrudRepository repository;
	private final RoleConverterService converter;
	
	@Autowired
	public RoleCrudService(RoleCrudRepository repository, RoleConverterService converter) {
		super(repository, converter);
		this.repository = repository;
		this.converter = converter;
	}
	
	@Override
	public RoleDto create(RoleDto roleDto) {
		if (roleDto != null && repository.findByName(roleDto.getName()).isEmpty()) {
			roleDto.setId(null); // to make sure we are creating and not updating
			Role savedRole = repository.save(converter.convertToEntity(roleDto));
			return converter.convertToDto(savedRole);			
		}
		return null;
	}
	
	@Override
	public RoleDto update(Integer id, Map<String, String> changes) {		
		RoleDto existingRoleDto = read(id);
		if (existingRoleDto != null && changes != null) {
			if (changes.containsKey("name")) {
				if (repository.findByName(changes.get("name")).isPresent()) {
					throw new FitBuddyException("Role name already exists.");
				}
				existingRoleDto.setName(changes.get("name"));
			}
			return doUpdate(existingRoleDto);
		}
		return null;
	}

	@Override
	protected RoleDto doUpdate(RoleDto dto) {
		Role savedRole = repository.save(converter.convertToEntity(dto));
		return converter.convertToDto(savedRole);
	}

	public RoleDto readByName(String name) {
		Optional<Role> role = repository.findByName(name);
		if (role.isPresent()) {
			return converter.convertToDto(role.get());			
		} else {
			return null;
		}
	}

}
