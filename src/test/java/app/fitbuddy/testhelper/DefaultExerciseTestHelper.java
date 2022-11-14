package app.fitbuddy.testhelper;

import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.jpa.entity.DefaultExercise;
import app.fitbuddy.jpa.entity.Exercise;

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

	public static boolean isEqual(ExerciseDto exerciseDto, DefaultExercise exercise) {
		return exerciseDto.getName().equals(exercise.getName());
	}

	public static boolean isEqual(DefaultExercise exercise, ExerciseDto exerciseDto) {
		return isEqual(exerciseDto, exercise);
	}

}
