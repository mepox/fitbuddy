package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseRepository;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

@Service
public class ExerciseConverterService implements TwoWayConverterService<ExerciseDto, Exercise> {
	
	private final Logger logger;
	private final ExerciseRepository repository;
	
	@Autowired
	public ExerciseConverterService(ExerciseRepository repository) {
		this.repository = repository;
		this.logger = LoggerFactory.getLogger(ExerciseConverterService.class);
	}
	
	@Override
	public Exercise convertToEntity(Optional<ExerciseDto> dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ExerciseDto convertToDto(Optional<Exercise> entity) {
		if (entity.isPresent()) {
			return new ExerciseDto(entity.get().getId(), entity.get().getName(), entity.get().getAppUser().getId());
		}
		return null;
	}

	@Override
	public List<ExerciseDto> convertAllEntity(List<Exercise> entities) {
		List<ExerciseDto> dtos = null;
		if (entities != null) {
			dtos = new ArrayList<>();
			for (Exercise entity : entities) {
				ExerciseDto dto = convertToDto(Optional.of(entity));
				dtos.add(dto);
			}
		}
		return dtos;
	}

}
