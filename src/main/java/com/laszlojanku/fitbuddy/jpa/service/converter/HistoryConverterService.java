package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.entity.History;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class HistoryConverterService implements TwoWayConverterService<HistoryDto, History> {
	
	private final ExerciseCrudRepository exerciseCrudRepository;
	
	@Autowired
	public HistoryConverterService(ExerciseCrudRepository exerciseCrudRepository) {
		this.exerciseCrudRepository = exerciseCrudRepository;
	}

	@Override
	public History convertToEntity(HistoryDto dto) {
		if (dto != null) {
			AppUser appUser = new AppUser();
			appUser.setId(dto.getAppUserId());
					
			Exercise exercise = new Exercise();
			exercise.setId(exerciseCrudRepository.findIdByNameAndUserId(dto.getExerciseName(), dto.getAppUserId()));
			
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
			return new HistoryDto(entity.getId(), entity.getAppUser().getId(), entity.getExercise().getName(),
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
