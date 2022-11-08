package com.laszlojanku.fitbuddy.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.laszlojanku.fitbuddy.jpa.entity.Exercise;

public interface ExerciseCrudRepository extends CrudRepository<Exercise, Integer> {
	
	@Query(value = "SELECT * FROM exercise WHERE app_user_id = ?1", nativeQuery = true)
	List<Exercise> findAllByUserId(Integer userId);
	
	@Query(value = "SELECT id FROM exercise WHERE name = ?1 AND app_user_id = ?2", nativeQuery = true)
	Integer findIdByNameAndUserId(String name, Integer userId);
	
	Optional<Exercise> findByName(String name);
}
