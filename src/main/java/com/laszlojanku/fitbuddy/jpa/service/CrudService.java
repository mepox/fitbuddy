package com.laszlojanku.fitbuddy.jpa.service;

public interface CrudService<D> {
	
	public D create(D dto);
	public D read(int id);	
	public D update(D dto);
	public void delete(int id);
	
}
