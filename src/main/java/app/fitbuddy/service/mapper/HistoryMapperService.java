package app.fitbuddy.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryResponseDTO;
import app.fitbuddy.dto.history.HistoryUpdateDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Exercise;
import app.fitbuddy.entity.History;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.AppUserRepository;
import app.fitbuddy.repository.ExerciseRepository;

@Service
public class HistoryMapperService implements MapperService<HistoryRequestDTO, HistoryResponseDTO,
															HistoryUpdateDTO, History> {
	
	private final AppUserRepository appUserRepository;
	private final ExerciseRepository exerciseRepository;
	
	@Autowired
	public HistoryMapperService(AppUserRepository appUserRepository, 
								ExerciseRepository exerciseRepository) {
		this.appUserRepository = appUserRepository;
		this.exerciseRepository = exerciseRepository;
	}

	@Override
	public History requestDtoToEntity(HistoryRequestDTO requestDTO) {
		if (requestDTO == null) {
			return null;
		}
		Optional<AppUser> optionalAppUser = appUserRepository.findById(requestDTO.getAppUserId());
		if (optionalAppUser.isEmpty()) {
			throw new FitBuddyException("AppUser not found with ID: " + requestDTO.getAppUserId());
		}
		Optional<Exercise> optionalExercise = exerciseRepository.findByNameAndUserId(requestDTO.getExerciseName(),
																							requestDTO.getAppUserId());
		if (optionalExercise.isEmpty()) {
			throw new FitBuddyException("Exercise not found with name and ID: " + requestDTO.getExerciseName() +
										", " + requestDTO.getAppUserId());
		}		
		History history = new History();
		history.setAppUser(optionalAppUser.get());
		history.setExercise(optionalExercise.get());
		history.setWeight(requestDTO.getWeight());
		history.setReps(requestDTO.getReps());
		history.setCreatedOn(requestDTO.getCreatedOn());
		return history;
	}

	@Override
	public HistoryResponseDTO entityToResponseDto(History entity) {
		if (entity == null) {
			return null;
		}
		return new HistoryResponseDTO(entity.getId(), entity.getAppUser().getId(), entity.getExercise().getName(),
										entity.getWeight(), entity.getReps(), entity.getCreatedOn());
	}

	@Override
	public List<HistoryResponseDTO> entitiesToResponseDtos(List<History> entities) {
		if (entities == null || entities.isEmpty()) {
			return Collections.emptyList();
		}
		List<HistoryResponseDTO> result = new ArrayList<>();
		for (History entity : entities) {
			result.add(entityToResponseDto(entity));
		}
		return result;
	}

	@Override
	public History applyUpdateDtoToEntity(History entity, HistoryUpdateDTO updateDTO) {
		if (entity == null || updateDTO == null) {
			return null;
		}
		if (updateDTO.getWeight() != null) {
			entity.setWeight(updateDTO.getWeight());
		}
		if (updateDTO.getReps() != null) {
			entity.setReps(updateDTO.getReps());
		}
		return entity;		                                  
	}	

}
