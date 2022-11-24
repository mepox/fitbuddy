package app.fitbuddy.controller.crud;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.exercise.ExerciseRequestDTO;
import app.fitbuddy.dto.exercise.ExerciseResponseDTO;
import app.fitbuddy.dto.exercise.ExerciseUpdateDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.service.crud.AppUserCrudService;
import app.fitbuddy.service.crud.ExerciseCrudService;

@RestController
@RequestMapping("/user/exercises")
@PreAuthorize("authenticated")
public class ExerciseController {
	
	private final Logger logger;
	private final ExerciseCrudService exerciseCrudService;
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public ExerciseController(ExerciseCrudService exerciseCrudService, 
								AppUserCrudService appUserCrudService) {
		this.exerciseCrudService = exerciseCrudService;
		this.appUserCrudService = appUserCrudService;
		this.logger = LoggerFactory.getLogger(ExerciseController.class);
	}
	
	@PostMapping
	public void create(@RequestBody @Valid ExerciseRequestDTO exerciseRequestDTO) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer userId = appUserCrudService.readByName(auth.getName()).getId();
		if (userId != null) {	
			exerciseRequestDTO.setAppUserId(userId);
			exerciseCrudService.create(exerciseRequestDTO);
			logger.info("Creating new exercise: {}", exerciseRequestDTO);
		}
	}

	@GetMapping
	public List<ExerciseResponseDTO> readAll() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer userId = appUserCrudService.readByName(auth.getName()).getId();		
		if (userId != null) {
			List<ExerciseResponseDTO> responseDTOs = exerciseCrudService.readMany(userId);
			logger.info("Sending a list of exercises: {}", responseDTOs);
			return responseDTOs;
		}
		return Collections.emptyList();
	}
	
	@PutMapping("{id}")
	public void update(@PathVariable("id")  @NotNull Integer exerciseId, @Valid @RequestBody ExerciseUpdateDTO exerciseUpdateDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (userId != null) {
			exerciseCrudService.update(exerciseId, exerciseUpdateDTO);	
			logger.info("Updating the exercise: {}", exerciseUpdateDTO);
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id")  @NotNull Integer exerciseId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (userId != null) {
			ExerciseResponseDTO exerciseResponseDTO = exerciseCrudService.readById(exerciseId);				
			if (exerciseResponseDTO != null && exerciseResponseDTO.getAppUserId().equals(userId)) {
				exerciseCrudService.delete(exerciseId);
				logger.info("Deleting exercise: {}", exerciseResponseDTO);
			} else {
				throw new FitBuddyException("UserIds doesn't match. Cannot delete others Exercise.");
			}
		}
	}

}
