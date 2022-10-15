package com.laszlojanku.fitbuddy.jpa.service;

public interface TwoWayConverterService<D, E> {
	
	public E convertToEntity(D dto);
	public D convertToDto(E entity);

}
