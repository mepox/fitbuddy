package app.fitbuddy.jpa.service.crud;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.Exercise;
import app.fitbuddy.jpa.repository.ExerciseCrudRepository;
import app.fitbuddy.jpa.service.GenericCrudService;
import app.fitbuddy.jpa.service.converter.ExerciseConverterService;

@Service
@Validated
public class ExerciseCrudService extends GenericCrudService<ExerciseDto, Exercise> {
	
	private final ExerciseCrudRepository repository;
	private final ExerciseConverterService converter;
	
	@Autowired
	public ExerciseCrudService(ExerciseCrudRepository repository, ExerciseConverterService converter) {
		super(repository, converter);
		this.repository = repository;
		this.converter = converter;		
	}
	
	@Override
	public ExerciseDto create(ExerciseDto exerciseDto) {
		if (exerciseDto != null && repository.findByName(exerciseDto.getName()).isEmpty()) {
			exerciseDto.setId(null); // to make sure we are creating and not updating
			Exercise savedExercise = repository.save(converter.convertToEntity(exerciseDto));
			return converter.convertToDto(savedExercise);
		}
		return null;
	}
	
	@Override
	public ExerciseDto update(Integer id, Map<String, String> changes) {
		ExerciseDto existingDto = read(id);
		if (existingDto != null && changes != null) {
			if (changes.containsKey("name")) {
				if (repository.findByNameAndUserId(changes.get("name"), id).isPresent()) {
					throw new FitBuddyException("Exercise name already exists.");
				}
				existingDto.setName(changes.get("name"));
			}
			return doUpdate(existingDto);
		}
		return null;
	}
	
	@Override
	protected ExerciseDto doUpdate(@Valid ExerciseDto dto) {
		Exercise savedExercise = repository.save(converter.convertToEntity(dto));		
		return converter.convertToDto(savedExercise);
	}

	@NotNull	
	public List<ExerciseDto> readMany(Integer userId) {
		List<ExerciseDto> exerciseDtos = Collections.emptyList();
		List<Exercise> exercises = repository.findAllByUserId(userId);
		
		if (!exercises.isEmpty()) {
			exerciseDtos = converter.convertAllEntity(exercises);
		}
	
		return exerciseDtos;
	}
	
}
