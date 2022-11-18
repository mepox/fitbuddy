package app.fitbuddy.repository;

import org.springframework.data.repository.CrudRepository;

import app.fitbuddy.entity.DefaultExercise;

public interface DefaultExerciseRepository extends CrudRepository<DefaultExercise, Integer> {

}
