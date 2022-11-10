package app.fitbuddy.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.fitbuddy.jpa.entity.Role;

public interface RoleCrudRepository extends CrudRepository<Role, Integer> {
	
	@Query(value = "SELECT * FROM role WHERE role.name = ?1", nativeQuery = true)
	Optional<Role> findByName(String name);

}
