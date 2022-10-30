package com.laszlojanku.fitbuddy.jpa.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.RoleDto;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.RoleCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.RoleConverterService;

@Service
public class RoleCrudService extends GenericCrudService<RoleDto, Role> {
	
	private final RoleCrudRepository roleCrudRepository;
	private final RoleConverterService roleConverterService;
	
	@Autowired
	public RoleCrudService(RoleCrudRepository roleCrudRepository, RoleConverterService roleConverterService) {
		super(roleCrudRepository, roleConverterService);
		this.roleCrudRepository = roleCrudRepository;
		this.roleConverterService = roleConverterService;
	}

	@Override
	public RoleDto update(Integer id, RoleDto dto) {
		RoleDto existingDto = read(id);
		if (existingDto != null) {
			if (dto.getName() != null) {
				existingDto.setName(dto.getName());
			}
			Role role = roleConverterService.convertToEntity(existingDto);
			roleCrudRepository.save(role);
			return existingDto;
		}
		return null;
	}

}
