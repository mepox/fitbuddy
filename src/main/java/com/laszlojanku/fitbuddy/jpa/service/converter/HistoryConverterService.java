package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public History convertToEntity(Optional<HistoryDto> dto) {
		if (dto.isPresent()) {
			AppUser appUser = new AppUser();
			appUser.setId(dto.get().getAppUserId());
			
			Exercise exercise = new Exercise();
			exercise.setId(dto.get().getExerciseId());
			
			History history = new History();
			history.setId(dto.get().getId());
			history.setWeight(dto.get().getWeight());
			history.setReps(dto.get().getReps());
			history.setCreatedOn(dto.get().getCreatedOn());
			
			history.setAppUser(appUser);
			history.setExercise(exercise);
			
			return history;
		}
		return null;
	}

	@Override
	public HistoryDto convertToDto(Optional<History> entity) {
		if (entity.isPresent()) {
			return new HistoryDto(entity.get().getId(), entity.get().getAppUser().getId(), 
									entity.get().getExercise().getId(),
									entity.get().getWeight(), entity.get().getReps(), entity.get().getCreatedOn());
		}
		return null;
	}

	@Override
	public List<HistoryDto> convertAllEntity(List<History> entities) {
		List<HistoryDto> dtos = null;
		if (entities != null) {
			dtos = new ArrayList<>();
			for (History entity : entities) {
				HistoryDto dto = convertToDto(Optional.of(entity));
				dtos.add(dto);
			}
		}
		return dtos;
	}

}
