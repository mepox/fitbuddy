package com.laszlojanku.fitbuddy.jpa.service;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public abstract class GenericCrudService<D, E> implements CrudService<D> {
	
	private final CrudRepository<E, Integer> repository;
	private final TwoWayConverterService<D, E> converter;
	
	public GenericCrudService(CrudRepository<E, Integer> repository, TwoWayConverterService<D, E> converter) {
		this.repository = repository;
		this.converter = converter;
	}
	
	public abstract Optional<E> getExisting (D dto);	

	@Override
	public D create(D dto) {
		if (dto != null) {
			E entity = converter.convertToEntity(dto);
			if (entity != null) {
				repository.save(entity);
				return dto;
			}
		}
		return null;
	}

	@Override
	public D read(Integer id) {
		Optional<E> entity = repository.findById(id);		
		return converter.convertToDto(entity.get());
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);		
	}
	
}
