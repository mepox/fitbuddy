package app.fitbuddy.jpa.service;

import java.util.Map;

public interface CrudService<D> {
	
	public D create(D dto);
	public D read(Integer id);
	public D update(Integer id, Map<String, String> changes);
	public void delete(Integer id);
	
}
