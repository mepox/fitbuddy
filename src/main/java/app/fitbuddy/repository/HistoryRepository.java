package app.fitbuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.fitbuddy.entity.History;

public interface HistoryRepository extends CrudRepository<History, Integer> {
	
	@Query(value = "SELECT * FROM history WHERE app_user_id = ?1 AND created_on = ?2", nativeQuery = true)
	List<History> findAllByUserIdAndDate(Integer userId, String date); 

}
