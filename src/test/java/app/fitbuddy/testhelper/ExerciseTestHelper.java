package app.fitbuddy.testhelper;

import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.jpa.entity.AppUser;
import app.fitbuddy.jpa.entity.Exercise;

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
	
	public static boolean isEqual(ExerciseDto exerciseDto, Exercise exercise) {
		return (exerciseDto.getId().equals(exercise.getId()) &&
				exerciseDto.getName().equals(exercise.getName()) &&
				exerciseDto.getAppUserId().equals(exercise.getAppUser().getId()));
	}
	
	public static boolean isEqual(Exercise exercise, ExerciseDto exerciseDto) {
		return isEqual(exerciseDto, exercise);
	}

}
