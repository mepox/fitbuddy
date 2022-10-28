package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.exception.FitBuddyException;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Role;
import com.laszlojanku.fitbuddy.jpa.repository.RoleCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class AppUserConverterService implements TwoWayConverterService<AppUserDto, AppUser> {
	
	private final RoleCrudRepository roleCrudRepository;
	
	@Autowired
	public AppUserConverterService(RoleCrudRepository roleCrudRepository) {
		this.roleCrudRepository = roleCrudRepository;
	}

	@Override
	public AppUser convertToEntity(AppUserDto dto) {
		if (dto != null) {
			Optional<Role> role = roleCrudRepository.findByName(dto.getRolename());
			if (role.isEmpty()) {
				throw new FitBuddyException("Internal server error - role doesn't exists with name: " + dto.getRolename());
			}
			
			AppUser appUser = new AppUser();
			appUser.setId(dto.getId());
			appUser.setName(dto.getName());
			appUser.setPassword(dto.getPassword());
			appUser.setRole(role.get());
			
			return appUser;
		}
		return null;
	}

	@Override
	public AppUserDto convertToDto(AppUser entity) {
		if (entity != null) {
			return new AppUserDto(entity.getId(), entity.getName(), entity.getPassword(), entity.getRole().getName());
		}
		return null;
	}

	@Override
	public List<AppUserDto> convertAllEntity(List<AppUser> entities) {
		List<AppUserDto> dtos = null;
		if (entities != null) {
			dtos = new ArrayList<>();
			for (AppUser appUser : entities) {
				AppUserDto dto = convertToDto(appUser);
				dtos.add(dto);
			}
		}
		return null;
	}
	
}
