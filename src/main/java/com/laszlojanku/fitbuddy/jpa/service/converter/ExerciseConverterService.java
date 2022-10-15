package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.ArrayList;
import java.util.List;

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
	public Exercise convertToEntity(ExerciseDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ExerciseDto convertToDto(Exercise entity) {		
		return new ExerciseDto(entity.getId(), entity.getName(), entity.getAppUser().getId());
	}

	@Override
	public List<ExerciseDto> convertAllEntity(List<Exercise> entities) {
		List<ExerciseDto> dtos = null;
		if (entities != null) {
			dtos = new ArrayList<>();
			for (Exercise entity : entities) {
				ExerciseDto dto = convertToDto(entity);
				dtos.add(dto);
			}
		}
		return dtos;
	}

}
