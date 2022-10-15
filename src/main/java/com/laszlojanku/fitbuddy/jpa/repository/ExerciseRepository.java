package com.laszlojanku.fitbuddy.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.laszlojanku.fitbuddy.jpa.entity.Exercise;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {
	
	@Query(value = "SELECT * FROM exercise WHERE app_user_id = ?1", nativeQuery = true)
	List<Exercise> findAllByUserId(Integer userId);

}
