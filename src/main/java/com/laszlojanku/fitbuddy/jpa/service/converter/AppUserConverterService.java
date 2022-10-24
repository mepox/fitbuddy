package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class AppUserConverterService implements TwoWayConverterService<AppUserDto, AppUser> {

	@Override
	public AppUser convertToEntity(AppUserDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUserDto convertToDto(AppUser entity) {
		if (entity != null) {
			return new AppUserDto(entity.getId(), entity.getName(), entity.getRole().getName());
		}
		return null;
	}

	@Override
	public List<AppUserDto> convertAllEntity(List<AppUser> entities) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
