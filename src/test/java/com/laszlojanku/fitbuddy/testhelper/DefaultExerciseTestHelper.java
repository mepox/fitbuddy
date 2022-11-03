package com.laszlojanku.fitbuddy.testhelper;

import com.laszlojanku.fitbuddy.jpa.entity.DefaultExercise;

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

}
