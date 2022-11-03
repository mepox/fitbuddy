package com.laszlojanku.fitbuddy.testhelper;

import com.laszlojanku.fitbuddy.jpa.entity.AppUser;
import com.laszlojanku.fitbuddy.jpa.entity.Exercise;
import com.laszlojanku.fitbuddy.jpa.entity.History;

public class HistoryTestHelper {
	
	private static final Integer ID = 1;
	private static final Integer WEIGHT = 11;
	private static final Integer REPS = 111;
	private static final String CREATED_ON = "01-01-2022";
	
	public static History getMockHistory() {
		return getMockHistory(ID, WEIGHT, REPS, CREATED_ON, 
				ExerciseTestHelper.getMockExercise(), AppUserTestHelper.getMockAppUser());

	}
	
	public static History getMockHistory(Integer id, Integer weight, Integer reps, String createdOn) {
		return getMockHistory(id, weight, reps, createdOn, 
				ExerciseTestHelper.getMockExercise(), AppUserTestHelper.getMockAppUser());
	}
	
	public static History getMockHistory(Integer id, Integer weight, Integer reps, String createdOn, 
			Exercise exercise, AppUser appUser) {
		History history = new History();
		history.setId(id);
		history.setWeight(weight);
		history.setReps(reps);
		history.setCreatedOn(createdOn);
		history.setExercise(exercise);
		history.setAppUser(appUser);
		return history;
	}
	
	
	

}
