package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.RoleDto;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class RoleConverterService implements TwoWayConverterService<RoleDto, Role>{

	@Override
	public Role convertToEntity(RoleDto dto) {
		if (dto != null) {
			Role role = new Role();
			role.setId(dto.getId());
			role.setName(dto.getName());
			return role;
		}
		return null;
	}

	@Override
	public RoleDto convertToDto(Role entity) {
		if (entity != null) {
			return new RoleDto(entity.getId(), entity.getName());
		}
		return null;
	}

	@Override
	public List<RoleDto> convertAllEntity(List<Role> entities) {
		List<RoleDto> dtos = null;
		if (entities != null) {
			dtos = new ArrayList<>();
			for (Role entity : entities) {
				RoleDto dto = convertToDto(entity);
				dtos.add(dto);				
			}			
		}		
		return dtos;
	}

}
