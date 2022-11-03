package com.laszlojanku.fitbuddy.operation.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.validation.Valid;

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
import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.jpa.service.crud.AppUserCrudService;
import com.laszlojanku.fitbuddy.jpa.service.crud.HistoryCrudService;

@RestController
@RequestMapping("/user/history")
public class HistoryController {
	
	private final Logger logger;
	private final HistoryCrudService historyCrudService;
	private final AppUserCrudService appUserCrudService;
	
	@Autowired
	public HistoryController(HistoryCrudService historyCrudService, AppUserCrudService appUserCrudService) {
		this.historyCrudService = historyCrudService;
		this.appUserCrudService = appUserCrudService;
		this.logger = LoggerFactory.getLogger(HistoryController.class);
	}
	
	@PostMapping
	public void create(Authentication auth, @Valid @RequestBody HistoryDto historyDto) {
		if (auth != null) {
			AppUserDto appUserDto = appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {
				historyDto.setId(null);
				historyDto.setAppUserId(appUserDto.getId());
				historyCrudService.create(historyDto);
				logger.info("Creating new history: {}", historyDto);
			}
		}		
	}
	
	@GetMapping("{date}")
	public List<HistoryDto> readAll(@PathVariable("date") String strDate, Authentication auth) {
		if (auth != null && strDate != null) {			
			try {
				LocalDate.parse(strDate);
			} catch (DateTimeParseException e) {
				return null;
			}
			
			AppUserDto appUserDto = appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {
				List<HistoryDto> historyDtos = historyCrudService.readMany(appUserDto.getId(), strDate);
				logger.info("Sending a history for: {}", strDate);
				return historyDtos;
			}
		}
		return null;
	}
	
	@PutMapping("{id}")
	public void update(@PathVariable("id") Integer historyId, Authentication auth, @Valid @RequestBody HistoryDto historyDto) {
		if (auth != null && historyId != null) {
			AppUserDto appUserDto = appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {				
				historyDto.setAppUserId(appUserDto.getId());
				historyCrudService.update(historyId, historyDto);
				logger.info("Updating history: {}", historyDto);
				
			}
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer historyId, Authentication auth) {
		if (auth != null && historyId != null) {
			AppUserDto appUserDto = appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {
				HistoryDto historyDto = historyCrudService.read(historyId);				
				if (historyDto != null && historyDto.getAppUserId().equals(appUserDto.getId())) {
					historyCrudService.delete(historyId);
					logger.info("Deleting history: {}", historyDto);
				}
			}
		}	
	}
}
