package app.fitbuddy.jpa.service.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.AppUserDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.AppUser;
import app.fitbuddy.jpa.repository.AppUserCrudRepository;
import app.fitbuddy.jpa.service.GenericCrudService;
import app.fitbuddy.jpa.service.converter.AppUserConverterService;

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
	public AppUserDto update(Integer id, Map<String, String> changes) {		
		AppUserDto existingAppUserDto = read(id);
		if (existingAppUserDto != null && changes != null) {
			if (changes.containsKey("name")) {
				if (repository.findByName(changes.get("name")).isPresent()) {
					throw new FitBuddyException("Username already exists.");
				}
				existingAppUserDto.setName(changes.get("name"));
			}			
			if (changes.containsKey("password")) {
				existingAppUserDto.setPassword(changes.get("password"));
			}
			
			if (changes.containsKey("rolename")) {
				existingAppUserDto.setRolename(changes.get("rolename"));
			}
			return doUpdate(existingAppUserDto);			
		}
		return null;
	}

	@Override
	protected AppUserDto doUpdate(@Valid AppUserDto dto) {
		AppUser savedAppUser = repository.save(converter.convertToEntity(dto));
		return converter.convertToDto(savedAppUser);
	}

	public AppUserDto readByName(String name) {
		Optional<AppUser> appUser =	repository.findByName(name);
		if (appUser.isPresent()) {
			return converter.convertToDto(appUser.get());			
		} else {
			return null;
		}
	}
	
	@NotNull
	public List<AppUserDto> readMany() {
		List<AppUserDto> result = new ArrayList<>();
		for (AppUser appUser : repository.findAll()) {
			result.add(converter.convertToDto(appUser));
		}
		return result;
	}
	
}
