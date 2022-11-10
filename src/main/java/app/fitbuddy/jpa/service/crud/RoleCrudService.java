package app.fitbuddy.jpa.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.RoleDto;
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
	public RoleDto update(Integer id, RoleDto roleDto) {
		RoleDto existingRoleDto = read(id);
		if (existingRoleDto != null && roleDto != null && repository.findByName(roleDto.getName()).isEmpty()) {
			if (roleDto.getName() != null) {
				existingRoleDto.setName(roleDto.getName());
			}			
			Role savedRole = repository.save(converter.convertToEntity(existingRoleDto));
			return converter.convertToDto(savedRole);
		}
		return null;
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
