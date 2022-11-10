package app.fitbuddy.jpa.service;

import java.util.List;

public interface TwoWayConverterService<D, E> {
	
	public E convertToEntity(D dto);
	public D convertToDto(E entity);
	public List<D> convertAllEntity (List<E> entities);

}
