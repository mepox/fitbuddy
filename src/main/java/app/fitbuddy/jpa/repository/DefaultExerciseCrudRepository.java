package app.fitbuddy.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import app.fitbuddy.jpa.entity.DefaultExercise;

public interface DefaultExerciseCrudRepository extends CrudRepository<DefaultExercise, Integer> {

}
