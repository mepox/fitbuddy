package com.laszlojanku.fitbuddy.jpa.service;

import java.util.List;
import java.util.Optional;

public interface TwoWayConverterService<D, E> {
	
	public E convertToEntity(Optional<D> dto);
	public D convertToDto(Optional<E> entity);
	public List<D> convertAllEntity (List<E> entities);

}
