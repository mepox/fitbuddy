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
	
	@Override
	public HistoryDto create(HistoryDto historyDto) {
		if (historyDto != null) {
			historyDto.setId(null); // to make sure we are creating and not updating
			History savedHistory = repository.save(converter.convertToEntity(historyDto));
			return converter.convertToDto(savedHistory);			
		}
		return null;
	}
	
	@Override
	public HistoryDto update(Integer id, HistoryDto historyDto) {
		HistoryDto existingHistoryDto = read(id);
		if (existingHistoryDto != null && historyDto != null) {			
			if (historyDto.getWeight() != null) {
				existingHistoryDto.setWeight(historyDto.getWeight());
			}
			if (historyDto.getReps() != null) {
				existingHistoryDto.setReps(historyDto.getReps());
			}
			if (historyDto.getCreatedOn() != null) {
				existingHistoryDto.setCreatedOn(historyDto.getCreatedOn());
			}			
			History savedHistory = repository.save(converter.convertToEntity(existingHistoryDto));
			return converter.convertToDto(savedHistory);
		}
		return null;
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

}
