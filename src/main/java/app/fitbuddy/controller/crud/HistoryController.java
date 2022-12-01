package app.fitbuddy.controller.crud;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Collections;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryResponseDTO;
import app.fitbuddy.dto.history.HistoryUpdateDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.security.AppUserPrincipal;
import app.fitbuddy.service.crud.HistoryCrudService;

@RestController
@RequestMapping("/user/history")
@PreAuthorize("authenticated")
public class HistoryController {

    private final Logger logger;
    private final HistoryCrudService historyCrudService;
    private final String DATE_NOT_VALID = "Date is not valid";

    @Autowired
    public HistoryController(HistoryCrudService historyCrudService) {
        this.historyCrudService = historyCrudService;
        this.logger = LoggerFactory.getLogger(HistoryController.class);
    }

    @PostMapping
    public void create(@Valid @RequestBody HistoryRequestDTO historyRequestDTO,
    		@AuthenticationPrincipal AppUserPrincipal appUserPrincipal) {
        Integer userId = appUserPrincipal.getId();
        if (userId != null) {
            historyRequestDTO.setAppUserId(userId);
            historyCrudService.create(historyRequestDTO);
            logger.info("Creating new history: {}", historyRequestDTO);
        }
    }

    @GetMapping("{date}")
    public List<HistoryResponseDTO> readAll(@PathVariable("date") String strDate,
    		@AuthenticationPrincipal AppUserPrincipal appUserPrincipal) {
        if (strDate != null) {
            try {
                LocalDate.parse(strDate);
            } catch (DateTimeParseException e) {
                throw new FitBuddyException(DATE_NOT_VALID);
            }
            Integer userId = appUserPrincipal.getId();
            if (userId != null) {
                List<HistoryResponseDTO> historyResponseDTOs = historyCrudService.readMany(userId, strDate);
                logger.info("Sending a history for: {}", strDate);
                return historyResponseDTOs;
            }
        }
        return Collections.emptyList();
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") @NotNull Integer historyId,
    		@Valid @RequestBody HistoryUpdateDTO historyUpdateDTO,
    		@AuthenticationPrincipal AppUserPrincipal appUserPrincipal) {
        Integer userId = appUserPrincipal.getId();
        if (userId != null) {
        	HistoryResponseDTO historyResponseDTO = historyCrudService.readById(historyId);
        	if (historyResponseDTO != null && historyResponseDTO.getAppUserId().equals(userId)) {
        		historyCrudService.update(historyId, historyUpdateDTO);
                logger.info("Updating history: {}", historyUpdateDTO);
        	} else {
        		throw new FitBuddyException("UserIds doesn't match. Cannot update others History.");
        	}            
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") @NotNull Integer historyId,
    		@AuthenticationPrincipal AppUserPrincipal appUserPrincipal) {
        Integer userId = appUserPrincipal.getId();
        if (userId != null) {
            HistoryResponseDTO historyResponseDTO = historyCrudService.readById(historyId);
            if (historyResponseDTO != null && historyResponseDTO.getAppUserId().equals(userId)) {
                historyCrudService.delete(historyId);
                logger.info("Deleting history: {}", historyResponseDTO);
            } else {
                throw new FitBuddyException("UserIds doesn't match. Cannot delete others History.");
            }
        }
    }
}
