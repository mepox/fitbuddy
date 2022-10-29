package com.laszlojanku.fitbuddy.jpa.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.repository.AppUserCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.AppUserConverterService;

@Service
public class AppUserCrudService extends GenericCrudService<AppUserDto, AppUser> {
	
	private final AppUserCrudRepository userRepository;
	private final AppUserConverterService converter;
	
	@Autowired
	public AppUserCrudService(AppUserCrudRepository userRepository, AppUserConverterService converter) {
		super(userRepository, converter);
		this.userRepository = userRepository;
		this.converter = converter;
	}
	
	public AppUserDto readByName(String name) {
		Optional<AppUser> appUser =	userRepository.findByName(name);
		if (appUser.isPresent()) {
			return converter.convertToDto(appUser.get());			
		} else {
			return null;
		}
	}

	@Override
	public AppUserDto update(Integer id, AppUserDto dto) {
		AppUserDto existingDto = read(id);
		if (existingDto != null) {
			if (dto.getName() != null) {
				existingDto.setName(dto.getName());
			}
			if (dto.getPassword() != null) {
				existingDto.setPassword(dto.getPassword());
			}
			if (dto.getRolename() != null) {
				existingDto.setRolename(dto.getRolename());
			}
			AppUser appUser = converter.convertToEntity(existingDto);
			userRepository.save(appUser);
			return existingDto;
		}
		return null;
	}
	
}
