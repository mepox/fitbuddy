package app.fitbuddy.testhelper;

import app.fitbuddy.dto.exercise.ExerciseResponseDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Exercise;

public class ExerciseTestHelper {
	
	private static final Integer ID = 1;
	private static final String NAME = "exerciseName";
	
	public static Exercise getMockExercise() {
		return getMockExercise(ID, NAME, AppUserTestHelper.getMockAppUser());
	}
	
	public static Exercise getMockExercise(Integer id, String name) {
		return getMockExercise(id, name, AppUserTestHelper.getMockAppUser());
	}
	
	public static Exercise getMockExercise(Integer id, String name, AppUser appUser) {
		Exercise exercise = new Exercise();
		exercise.setId(id);
		exercise.setName(name);
		exercise.setAppUser(appUser);
		return exercise;
	}
	
	public static boolean isEqual(ExerciseResponseDTO exerciseResponseDTO, Exercise exercise) {
		return (exerciseResponseDTO.getId().equals(exercise.getId()) &&
				exerciseResponseDTO.getName().equals(exercise.getName()) &&
				exerciseResponseDTO.getAppUserId().equals(exercise.getAppUser().getId()));
	}
	
	public static boolean isEqual(Exercise exercise, ExerciseResponseDTO exerciseResponseDTO) {
		return isEqual(exerciseResponseDTO, exercise);
	}

}
