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
	
	private final AppUserCrudRepository repository;
	private final AppUserConverterService converter;
	
	@Autowired
	public AppUserCrudService(AppUserCrudRepository repository, AppUserConverterService converter) {
		super(repository, converter);
		this.repository = repository;
		this.converter = converter;
	}
	
	@Override
	public AppUserDto create(AppUserDto appUserDto) {
		if (appUserDto != null && repository.findByName(appUserDto.getName()).isEmpty()) {	
			appUserDto.setId(null); // to make sure we are creating and not updating
			AppUser savedAppUser = repository.save(converter.convertToEntity(appUserDto));
			return converter.convertToDto(savedAppUser);			
		}
		return null;
	}
	
	@Override
	public AppUserDto update(Integer id, AppUserDto appUserDto) {
		AppUserDto existingAppUserDto = read(id);
		if (existingAppUserDto != null && appUserDto != null && repository.findByName(appUserDto.getName()).isEmpty()) {
			if (appUserDto.getName() != null) {
				existingAppUserDto.setName(appUserDto.getName());
			}
			if (appUserDto.getPassword() != null) {
				existingAppUserDto.setPassword(appUserDto.getPassword());
			}
			if (appUserDto.getRolename() != null) {
				existingAppUserDto.setRolename(appUserDto.getRolename());
			}			
			AppUser savedAppUser = repository.save(converter.convertToEntity(existingAppUserDto));
			return converter.convertToDto(savedAppUser);
		}
		return null;
	}

	public AppUserDto readByName(String name) {
		Optional<AppUser> appUser =	repository.findByName(name);
		if (appUser.isPresent()) {
			return converter.convertToDto(appUser.get());			
		} else {
			return null;
		}
	}
	
}
