package com.laszlojanku.fitbuddy.operation.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

import com.laszlojanku.fitbuddy.dto.AppUserDto;
import com.laszlojanku.fitbuddy.dto.HistoryDto;
import com.laszlojanku.fitbuddy.exception.FitBuddyException;
import com.laszlojanku.fitbuddy.jpa.service.crud.AppUserCrudService;
import com.laszlojanku.fitbuddy.jpa.service.crud.HistoryCrudService;

@RestController
@RequestMapping("/user/history")
@PreAuthorize("authenticated")
public class HistoryController {
	
	private final Logger logger;
	private final HistoryCrudService historyCrudService;
	private final AppUserCrudService appUserCrudService;
	private final String DATE_NOT_VALID = "Date is not valid";
	
	@Autowired
	public HistoryController(HistoryCrudService historyCrudService, AppUserCrudService appUserCrudService) {
		this.historyCrudService = historyCrudService;
		this.appUserCrudService = appUserCrudService;
		this.logger = LoggerFactory.getLogger(HistoryController.class);
	}
	
	@PostMapping
	public void create(@Valid @RequestBody HistoryDto historyDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			try {
				LocalDate.parse(historyDto.getCreatedOn());
			} catch (DateTimeParseException e) {
				throw new FitBuddyException(DATE_NOT_VALID);
			}
			Integer userId = appUserCrudService.readByName(auth.getName()).getId();
			if (userId != null) {				
				historyDto.setAppUserId(userId);
				historyCrudService.create(historyDto);
				logger.info("Creating new history: {}", historyDto);
			}
	}
	
	@GetMapping("{date}")
	public List<HistoryDto> readAll(@PathVariable("date") String strDate) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (strDate != null) {
			try {
				LocalDate.parse(strDate);
			} catch (DateTimeParseException e) {
				throw new FitBuddyException(DATE_NOT_VALID);
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
	public void update(@PathVariable("id") Integer historyId, @Valid @RequestBody HistoryDto historyDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (historyId != null) {
			try {
				LocalDate.parse(historyDto.getCreatedOn());
			} catch (DateTimeParseException e) {
				throw new FitBuddyException(DATE_NOT_VALID);
			}
			AppUserDto appUserDto = appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {				
				historyDto.setAppUserId(appUserDto.getId());
				historyCrudService.update(historyId, historyDto);
				logger.info("Updating history: {}", historyDto);				
			}
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer historyId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (historyId != null) {
			AppUserDto appUserDto = appUserCrudService.readByName(auth.getName());
			if (appUserDto != null && appUserDto.getId() != null) {
				HistoryDto historyDto = historyCrudService.read(historyId);				
				if (historyDto != null && historyDto.getAppUserId().equals(appUserDto.getId())) {
					historyCrudService.delete(historyId);
					logger.info("Deleting history: {}", historyDto);
				} else {
					throw new FitBuddyException("UserIds doesn't match. Cannot delete others History.");
				}
			}
		}	
	}
}
