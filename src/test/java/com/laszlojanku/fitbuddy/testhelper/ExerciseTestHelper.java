package com.laszlojanku.fitbuddy.testhelper;

import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;

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

}
