package com.laszlojanku.fitbuddy.operation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laszlojanku.fitbuddy.dto.ExerciseDto;

@RestController
@RequestMapping("/user/exercise")
public class ExerciseController {
	
	@PostMapping
	public void create() {
		
	}
	
	@GetMapping
	public List<ExerciseDto> readAll() {
		return null;
	}
	
	@PutMapping()
	public void update() {
		
	}
	
	@DeleteMapping()
	public void delete() {
		
	}

}
