package com.laszlojanku.fitbuddy.jpa.service.crud;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.ExerciseConverterService;

public class ExerciseCrudService extends GenericCrudService<ExerciseDto, Exercise> {
	
	private final Logger logger;
	private final ExerciseRepository repository;
	private final ExerciseConverterService converter;
	
	public ExerciseCrudService(ExerciseRepository repository, ExerciseConverterService converter) {
		super(repository, converter);
		this.repository = repository;
		this.converter = converter;
		this.logger = LoggerFactory.getLogger(ExerciseCrudService.class);
	}

	@Override
	public Optional<Exercise> getExisting(ExerciseDto dto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	public List<ExerciseDto> readMany(Integer userId) {
		List<ExerciseDto> exerciseDtos = null;
		List<Exercise> exercises = repository.findAllByUserId(userId);
		
		if (exercises != null) {
			exerciseDtos = converter.convertAllEntity(exercises);
		}
		return exerciseDtos;
	}

}
