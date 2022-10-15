package com.laszlojanku.fitbuddy.jpa.service.crud;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.entity.History;
import com.laszlojanku.fitbuddy.jpa.repository.HistoryCrudRepository;
import com.laszlojanku.fitbuddy.jpa.service.GenericCrudService;
import com.laszlojanku.fitbuddy.jpa.service.converter.HistoryConverterService;

@Service
public class HistoryCrudService extends GenericCrudService<HistoryDto, History> {
	
	private final Logger logger;
	private final HistoryCrudRepository repository;
	private final HistoryConverterService converter;
	
	@Autowired
	public HistoryCrudService(HistoryCrudRepository repository,
			HistoryConverterService converter) {
		super(repository, converter);
		this.repository = repository;
		this.converter = converter;		
		this.logger = LoggerFactory.getLogger(HistoryCrudService.class);
	}

	@Override
	public Optional<History> getExisting(HistoryDto dto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	public List<HistoryDto> readMany(Integer userId, String date) {
		List<HistoryDto> dtos = null;
		List<History> historyList = repository.findAllByUserIdAndDate(userId, date);
		
		if (historyList != null) {
			dtos = converter.convertAllEntity(historyList);
		}
		return dtos;
	}

}
