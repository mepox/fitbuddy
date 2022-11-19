package app.fitbuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.fitbuddy.entity.Exercise;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {
	
	@Query(value = "SELECT * FROM exercise WHERE app_user_id = ?1", nativeQuery = true)
	List<Exercise> findAllByUserId(Integer userId);
	
	@Query(value = "SELECT * FROM exercise WHERE name = ?1 AND app_user_id = ?2", nativeQuery = true)
	Optional<Exercise> findByNameAndUserId(String name, Integer userId);
	
	Optional<Exercise> findByName(String name);
}
