package app.fitbuddy.service.crud;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryResponseDTO;
import app.fitbuddy.dto.history.HistoryUpdateDTO;
import app.fitbuddy.entity.History;
import app.fitbuddy.repository.HistoryRepository;
import app.fitbuddy.service.mapper.HistoryMapperService;

@Service
public class HistoryCrudService implements CrudService<HistoryRequestDTO, HistoryResponseDTO, HistoryUpdateDTO> {
	
	private final HistoryRepository historyRepository;
	private final HistoryMapperService historyMapperService;
	
	@Autowired
	public HistoryCrudService(HistoryRepository historyRepository, HistoryMapperService historyMapperService) {		
		this.historyRepository = historyRepository;
		this.historyMapperService = historyMapperService;
	}
	
	@Override
	public HistoryResponseDTO create(HistoryRequestDTO requestDTO) {
		if (requestDTO == null) {
			return null;
		}
		History savedHistory = historyRepository.save(historyMapperService.requestDtoToEntity(requestDTO));
		return historyMapperService.entityToResponseDto(savedHistory);
	}
	
	@Override
	public HistoryResponseDTO readById(Integer id) {
		Optional<History> optionalHistory = historyRepository.findById(id);
		if (optionalHistory.isEmpty()) {
			return null;
		}
		return historyMapperService.entityToResponseDto(optionalHistory.get());
	}
	
	@NotNull
	public List<HistoryResponseDTO> readMany(Integer appUserId, String date) {
		return historyMapperService.entitiesToResponseDtos(historyRepository.findAllByUserIdAndDate(appUserId, date));
	}
	
	@Override
	public HistoryResponseDTO update(Integer id, HistoryUpdateDTO updateDTO) {
		if (updateDTO == null) {
			return null;
		}
		Optional<History> optionalExistingHistory = historyRepository.findById(id);
		if (optionalExistingHistory.isEmpty()) {
			return null;
		}
		History savedHistory = historyRepository.save(
				historyMapperService.applyUpdateDtoToEntity(optionalExistingHistory.get(), updateDTO));
		return historyMapperService.entityToResponseDto(savedHistory);
	}
	
	@Override
	public void delete(Integer id) {
		historyRepository.deleteById(id);		
	}

}
