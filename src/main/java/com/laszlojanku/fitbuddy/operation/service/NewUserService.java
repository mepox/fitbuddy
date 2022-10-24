package com.laszlojanku.fitbuddy.operation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.DefaultExercise;
import com.laszlojanku.fitbuddy.jpa.repository.DefaultExerciseCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.crud.ExerciseCrudService;

@Service
public class NewUserService {
	
	private final DefaultExerciseCrudRepository defaultExerciseRepository;
	private final ExerciseCrudService exerciseCrudService;
	
	public NewUserService(DefaultExerciseCrudRepository defaultExerciseRepository, ExerciseCrudService exerciseCrudService) {
		this.defaultExerciseRepository = defaultExerciseRepository;
		this.exerciseCrudService = exerciseCrudService;
	}
	
	public void addDefaultExercises(Integer appUserId) {
		Iterable<DefaultExercise> defaultExercises = defaultExerciseRepository.findAll();
		
		Logger logger = LoggerFactory.getLogger(NewUserService.class);
		logger.info(defaultExercises.toString());
		
		for (DefaultExercise defaultExercise : defaultExercises) {
			exerciseCrudService.create(new ExerciseDto(null, defaultExercise.getName(), appUserId));
		}
	}

}
