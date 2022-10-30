package com.laszlojanku.fitbuddy.operation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.entity.DefaultExercise;
import com.laszlojanku.fitbuddy.jpa.repository.DefaultExerciseCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.crud.ExerciseCrudService;

@Service
public class NewUserService {
	
	private final DefaultExerciseCrudRepository defaultExerciseRepository;
	private final ExerciseCrudService exerciseCrudService;
	
	@Autowired
	public NewUserService(DefaultExerciseCrudRepository defaultExerciseRepository, ExerciseCrudService exerciseCrudService) {
		this.defaultExerciseRepository = defaultExerciseRepository;
		this.exerciseCrudService = exerciseCrudService;
	}
	
	public void addDefaultExercises(Integer appUserId) {
		Iterable<DefaultExercise> defaultExercises = defaultExerciseRepository.findAll();
		
		for (DefaultExercise defaultExercise : defaultExercises) {
			exerciseCrudService.create(new ExerciseDto(null, defaultExercise.getName(), appUserId));
		}
	}

}
