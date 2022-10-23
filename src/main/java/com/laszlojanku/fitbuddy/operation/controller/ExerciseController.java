package com.laszlojanku.fitbuddy.operation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.dto.ExerciseDto;
import com.laszlojanku.fitbuddy.jpa.service.crud.AppUserCrudService;
import com.laszlojanku.fitbuddy.jpa.service.crud.ExerciseCrudService;

@RestController
@RequestMapping("/user/exercises")
public class ExerciseController {
	
	private final Logger logger;
	private final ExerciseCrudService exerciseCrudService;;
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public ExerciseController(ExerciseCrudService exerciseCrudService, 
								AppUserCrudService appUserCrudService) {
		this.exerciseCrudService = exerciseCrudService;
		this.appUserCrudService = appUserCrudService;
		this.logger = LoggerFactory.getLogger(ExerciseController.class);
	}
	
	@PostMapping
	public void create(Authentication auth, @RequestBody ExerciseDto exerciseDto) {		
		if (auth != null) {
			Integer userId = appUserCrudService.readByName(auth.getName()).getId();
			if (userId != null) {
				exerciseDto.setId(null);
				exerciseDto.setAppUserId(userId);	
				exerciseCrudService.create(exerciseDto);
				logger.info("Creating new exercise: " + exerciseDto);
			}			
		}	
	}
	
	@GetMapping	
	public List<ExerciseDto> readAll(Authentication auth) {
		if (auth != null) {			
			AppUserDto appUserDto =  appUserCrudService.readByName(auth.getName());						
			if (appUserDto != null && appUserDto.getId() != null) {
				List<ExerciseDto> dtos = exerciseCrudService.readMany(appUserDto.getId());
				logger.info("Sending a list of exercises.");
				return dtos;
			}
		}
		return null;
	}
	
	@PutMapping("{id}")
	public void update(@PathVariable("id") Integer exerciseId, Authentication auth, @RequestBody ExerciseDto exerciseDto) {
		if (auth != null && exerciseId != null) {
			Integer userId = appUserCrudService.readByName(auth.getName()).getId();
			if (userId != null) {				
				exerciseDto.setAppUserId(userId);
				exerciseCrudService.update(exerciseId, exerciseDto);	
				logger.info("Updating the exercise: " + exerciseDto);
			}
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer exerciseId, Authentication auth) {
		if (auth != null && exerciseId != null) {
			AppUserDto appUserDto =  appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {
				ExerciseDto exerciseDto = exerciseCrudService.read(exerciseId);				
				if (exerciseDto != null && exerciseDto.getAppUserId().equals(appUserDto.getId())) {
					exerciseCrudService.delete(exerciseId);
					logger.info("Deleting exercise: " + exerciseDto);
				}
			}
		}
	}

}
