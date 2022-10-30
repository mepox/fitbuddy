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

	@Override
	public D create(D dto) {
		if (dto != null) {
			E entity = converter.convertToEntity(dto);
			if (entity != null) {
				E savedEntity = repository.save(entity);
				return converter.convertToDto(savedEntity);
			}
		}
		return null;
	}

	@Override
	public D read(Integer id) {
		Optional<E> entity = repository.findById(id);
		if (entity.isPresent()) {
			return converter.convertToDto(entity.get());
		}
		return null;		
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);		
	}
	
}
