package app.fitbuddy.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.fitbuddy.jpa.entity.History;

public interface HistoryCrudRepository extends CrudRepository<History, Integer> {
	
	@Query(value = "SELECT * FROM history WHERE app_user_id = ?1 AND created_on = ?2", nativeQuery = true)
	List<History> findAllByUserIdAndDate(Integer userId, String date); 

}
