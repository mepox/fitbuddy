package app.fitbuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitbuddy.entity.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
	
	@Query(value = "SELECT * FROM app_user WHERE app_user.name = ?1", nativeQuery = true)	
	Optional<AppUser> findByName(String name);

}
