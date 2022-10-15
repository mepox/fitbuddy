package com.laszlojanku.fitbuddy.jpa.service.crud;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.repository.AppUserCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.AppUserConverterService;

@Service
public class AppUserCrudService extends GenericCrudService<AppUserDto, AppUser> {

	private final Logger logger;	
	private final AppUserCrudRepository userRepository;
	private final AppUserConverterService converter;
	
	@Autowired
	public AppUserCrudService(AppUserCrudRepository userRepository, AppUserConverterService converter) {
		super(userRepository, converter);
		this.userRepository = userRepository;
		this.converter = converter;
		this.logger = LoggerFactory.getLogger(AppUserCrudService.class);
	}

	@Override
	public Optional<AppUser> getExisting(AppUserDto dto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
}
