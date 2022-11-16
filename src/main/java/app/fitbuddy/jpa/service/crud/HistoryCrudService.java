package app.fitbuddy.jpa.service.crud;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.HistoryDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.History;
import app.fitbuddy.jpa.repository.HistoryCrudRepository;
import app.fitbuddy.jpa.service.GenericCrudService;
import app.fitbuddy.jpa.service.converter.HistoryConverterService;

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
	public HistoryDto update(Integer id, Map<String, String> changes) {
		HistoryDto existingHistoryDto = read(id);
		if (existingHistoryDto != null && changes != null) {	
			try {
				if (changes.containsKey("weight")) {
					existingHistoryDto.setWeight(Integer.parseInt(changes.get("weight")));
				}
				if (changes.containsKey("reps")) {
					existingHistoryDto.setReps(Integer.parseInt(changes.get("reps")));
				}
			}
			catch (NumberFormatException e) {
				throw new FitBuddyException("Weight and Reps must be a number");
			}
			return doUpdate(existingHistoryDto);
		}
		return null;
	}	

	@Override
	protected HistoryDto doUpdate(@Valid HistoryDto dto) {
		History savedHistory = repository.save(converter.convertToEntity(dto));
		return converter.convertToDto(savedHistory);
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
