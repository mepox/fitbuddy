package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.entity.History;
import com.laszlojanku.fitbuddy.jpa.repository.HistoryCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class HistoryConverterService implements TwoWayConverterService<HistoryDto, History> {
	
	private final Logger logger;
	private final HistoryCrudRepository repository;
	
	@Autowired
	public HistoryConverterService(HistoryCrudRepository repository) {
		this.repository = repository;
		this.logger = LoggerFactory.getLogger(HistoryConverterService.class);
	}

	@Override
	public History convertToEntity(HistoryDto dto) {
		if (dto != null) {
			AppUser appUser = new AppUser();
			appUser.setId(dto.getAppUserId());
			
			Exercise exercise = new Exercise();
			exercise.setId(dto.getExerciseId());
			
			History history = new History();
			history.setId(dto.getId());
			history.setWeight(dto.getWeight());
			history.setReps(dto.getReps());
			history.setCreatedOn(dto.getCreatedOn());
			
			history.setAppUser(appUser);
			history.setExercise(exercise);
			
			return history;
		}
		return null;
	}

	@Override
	public HistoryDto convertToDto(History entity) {
		if (entity != null) {
			return new HistoryDto(entity.getId(), entity.getAppUser().getId(), 
									entity.getExercise().getId(), entity.getExercise().getName(),
									entity.getWeight(), entity.getReps(), entity.getCreatedOn());
		}
		return null;
	}

	@Override
	public List<HistoryDto> convertAllEntity(List<History> entities) {
		List<HistoryDto> dtos = null;
		if (entities != null) {
			dtos = new ArrayList<>();
			for (History entity : entities) {
				HistoryDto dto = convertToDto(entity);
				dtos.add(dto);
			}
		}
		return dtos;
	}

}
