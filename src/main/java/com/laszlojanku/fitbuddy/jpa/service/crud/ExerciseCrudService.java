package com.laszlojanku.fitbuddy.jpa.service.crud;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.repository.ExerciseCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.ExerciseConverterService;

@Service
public class ExerciseCrudService extends GenericCrudService<ExerciseDto, Exercise> {
	
	private final Logger logger;
	private final ExerciseCrudRepository repository;
	private final ExerciseConverterService converter;
	
	@Autowired
	public ExerciseCrudService(ExerciseCrudRepository repository, ExerciseConverterService converter) {
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

	@Override
	public ExerciseDto update(Integer id, ExerciseDto dto) {
		ExerciseDto existingDto = read(id);
		if (existingDto != null) {
			if (dto.getName() != null) {
				existingDto.setName(dto.getName());
			}
			Exercise entity = converter.convertToEntity(existingDto);
			repository.save(entity);
			return existingDto;
		}
		return null;
	}

}
