package app.fitbuddy.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import app.fitbuddy.dto.role.RoleRequestDTO;
import app.fitbuddy.dto.role.RoleResponseDTO;
import app.fitbuddy.dto.role.RoleUpdateDTO;
import app.fitbuddy.entity.Role;

@Service
public class RoleMapperService implements MapperService<RoleRequestDTO, RoleResponseDTO, RoleUpdateDTO, Role> {

	@Override
	public Role requestDtoToEntity(RoleRequestDTO requestDTO) {
		if (requestDTO == null) {
			return null;
		}
		Role role = new Role();
		role.setName(requestDTO.getName());
		return role;
	}

	@Override
	public RoleResponseDTO entityToResponseDto(Role entity) {
		if (entity == null) {
			return null;
		}
		return new RoleResponseDTO(entity.getId(), entity.getName());
	}

	@Override
	public List<RoleResponseDTO> entitiesToResponseDtos(List<Role> entities) {
		if (entities == null || entities.isEmpty()) {
			return Collections.emptyList();
		}
		List<RoleResponseDTO> result = new ArrayList<>();
		for (Role entity : entities) {
			result.add(entityToResponseDto(entity));
		}
		return result;
	}

	@Override
	public Role applyUpdateDtoToEntity(Role entity, RoleUpdateDTO updateDTO) {
		if (entity == null || updateDTO == null) {
			return null;
		}
		if (updateDTO.getName() != null) {
			entity.setName(updateDTO.getName());
		}
		return entity;
	}

}
