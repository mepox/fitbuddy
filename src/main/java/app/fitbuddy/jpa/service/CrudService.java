package app.fitbuddy.jpa.service;

public interface CrudService<D> {
	
	public D create(D dto);
	public D read(Integer id);
	public D update(Integer id, D dto);
	public void delete(Integer id);
	
}
