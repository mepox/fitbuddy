package app.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.AppUser;
import app.fitbuddy.jpa.entity.Role;
import app.fitbuddy.jpa.repository.RoleCrudRepository;
import app.fitbuddy.jpa.service.TwoWayConverterService;

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
	
	@NotNull
	@Override
	public List<AppUserDto> convertAllEntity(List<AppUser> entities) {
		List<AppUserDto> dtos = Collections.emptyList();
		if (entities != null) {
			dtos = new ArrayList<>();
			for (AppUser appUser : entities) {
				AppUserDto dto = convertToDto(appUser);
				dtos.add(dto);
			}			
		}
		return dtos;
	}
	
}
