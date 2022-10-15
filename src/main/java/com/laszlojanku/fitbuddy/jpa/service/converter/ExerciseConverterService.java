package com.laszlojanku.fitbuddy.jpa.service.converter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseRepository;
import com.laszlojanku.fitbuddy.jpa.service.TwoWayConverterService;

public class ExerciseConverterService implements TwoWayConverterService<ExerciseDto, Exercise> {
	
	private final Logger logger;
	private final ExerciseRepository repository;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExerciseDto> convertAllEntity(List<Exercise> entities) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
