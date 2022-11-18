package app.fitbuddy.service.operation;

import app.fitbuddy.dto.exercise.ExerciseRequestDTO;
import app.fitbuddy.entity.DefaultExercise;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.DefaultExerciseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.service.crud.ExerciseCrudService;

@Service
public class NewUserService {
	
	private final DefaultExerciseRepository defaultExerciseRepository;
	private final ExerciseCrudService exerciseCrudService;
	
	@Autowired
	public NewUserService(DefaultExerciseRepository defaultExerciseRepository, ExerciseCrudService exerciseCrudService) {
		this.defaultExerciseRepository = defaultExerciseRepository;
		this.exerciseCrudService = exerciseCrudService;
	}
	
	public void addDefaultExercises(Integer appUserId) {
		if (appUserId == null || appUserId < 0) {
			throw new FitBuddyException("Internal server error - appUserId is not correct");
		}
		Iterable<DefaultExercise> defaultExercises = defaultExerciseRepository.findAll();

		for (DefaultExercise defaultExercise : defaultExercises) {
			exerciseCrudService.create(new ExerciseRequestDTO(defaultExercise.getName(), appUserId));
		}
	}

}
