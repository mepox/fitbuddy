package app.fitbuddy.operation.service;

import app.fitbuddy.exception.FitBuddyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.jpa.entity.DefaultExercise;
import app.fitbuddy.jpa.repository.DefaultExerciseCrudRepository;
import app.fitbuddy.jpa.service.crud.ExerciseCrudService;

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
		if (appUserId == null || appUserId < 0) {
			throw new FitBuddyException("Internal server error - appUserId is not correct");
		}
		Iterable<DefaultExercise> defaultExercises = defaultExerciseRepository.findAll();

		for (DefaultExercise defaultExercise : defaultExercises) {
			exerciseCrudService.create(new ExerciseDto(null, defaultExercise.getName(), appUserId));
		}
	}

}
