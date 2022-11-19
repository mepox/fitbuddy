package app.fitbuddy.testhelper;

import app.fitbuddy.dto.exercise.ExerciseRequestDTO;
import app.fitbuddy.entity.DefaultExercise;

public class DefaultExerciseTestHelper {
	
	private static final Integer ID = 1;
	private static final String NAME = "defaultExerciseName";
	
	public static DefaultExercise getMockDefaultExercise() {
		return getMockDefaultExercise(ID, NAME);
	}
	
	public static DefaultExercise getMockDefaultExercise(Integer id, String name) {
		DefaultExercise defaultExercise = new DefaultExercise();
		defaultExercise.setId(id);
		defaultExercise.setName(name);
		return defaultExercise;
	}

	public static boolean isEqual(ExerciseRequestDTO exerciseRequestDTO, DefaultExercise exercise) {
		return exerciseRequestDTO.getName().equals(exercise.getName());
	}

	public static boolean isEqual(DefaultExercise exercise, ExerciseRequestDTO exerciseRequestDTO) {
		return isEqual(exerciseRequestDTO, exercise);
	}

}
