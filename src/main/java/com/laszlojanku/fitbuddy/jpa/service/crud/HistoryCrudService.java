package com.laszlojanku.fitbuddy.jpa.service.crud;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.entity.History;
import com.laszlojanku.fitbuddy.jpa.repository.HistoryCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.HistoryConverterService;

@Service
public class HistoryCrudService extends GenericCrudService<HistoryDto, History> {
	
	private final HistoryCrudRepository repository;
	private final HistoryConverterService converter;
	
	@Autowired
	public HistoryCrudService(HistoryCrudRepository repository,
			HistoryConverterService converter) {
		super(repository, converter);
		this.repository = repository;
		this.converter = converter;
	}
	
	@NotNull
	public List<HistoryDto> readMany(Integer userId, String date) {
		List<HistoryDto> historyDto = Collections.emptyList();
		List<History> histories = repository.findAllByUserIdAndDate(userId, date);
		
		if (!histories.isEmpty()) {
			historyDto = converter.convertAllEntity(histories);
		}
		return historyDto;
	}

	@Override
	public HistoryDto update(Integer id, HistoryDto dto) {
		HistoryDto existingDto = read(id);
		if (existingDto != null && dto != null) {
			if (dto.getExerciseName() != null) {
				existingDto.setExerciseName(dto.getExerciseName());
			}
			if (dto.getWeight() != null) {
				existingDto.setWeight(dto.getWeight());
			}
			if (dto.getReps() != null) {
				existingDto.setReps(dto.getReps());
			}
			if (dto.getCreatedOn() != null) {
				existingDto.setCreatedOn(dto.getCreatedOn());
			}
			History entity = converter.convertToEntity(existingDto);			
			repository.save(entity);
			return existingDto;
		}
		return null;
	}	

}
