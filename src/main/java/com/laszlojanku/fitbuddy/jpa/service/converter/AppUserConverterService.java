package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.repository.AppUserCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class AppUserConverterService implements TwoWayConverterService<AppUserDto, AppUser> {
	
	private final Logger logger;
	private final AppUserCrudRepository userRepository;
	
	@Autowired
	public AppUserConverterService(AppUserCrudRepository userRepository) {
		this.userRepository = userRepository;
		this.logger = LoggerFactory.getLogger(AppUserConverterService.class);
	}

	@Override
	public AppUser convertToEntity(Optional<AppUserDto> dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUserDto convertToDto(Optional<AppUser> entity) {
		if (entity.isPresent()) {
			return new AppUserDto(entity.get().getId(), entity.get().getName(), entity.get().getRole().getName());
		}
		return null;
	}

	@Override
	public List<AppUserDto> convertAllEntity(List<AppUser> entities) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
