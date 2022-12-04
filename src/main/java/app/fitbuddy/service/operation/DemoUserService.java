package app.fitbuddy.service.operation;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import app.fitbuddy.config.DemoUserProperties;
import app.fitbuddy.dto.appuser.AppUserRequestDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.exercise.ExerciseResponseDTO;
import app.fitbuddy.dto.history.HistoryRequestDTO;
import app.fitbuddy.dto.history.HistoryResponseDTO;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.service.crud.AppUserCrudService;
import app.fitbuddy.service.crud.ExerciseCrudService;
import app.fitbuddy.service.crud.HistoryCrudService;

@Service
public class DemoUserService {
	
	private final AppUserCrudService appUserCrudService;
	private final NewUserService newUserService;
	private final ExerciseCrudService exerciseCrudService;
	private final HistoryCrudService historyCrudService;
	private final DemoUserProperties demoUserProperties;
	private Integer demoUserId;
	
	@Autowired
	public DemoUserService(AppUserCrudService appUserCrudService, NewUserService newUserService,
			ExerciseCrudService exerciseCrudService, HistoryCrudService historyCrudService,
			DemoUserProperties demoUserProperties) {
		super();
		this.appUserCrudService = appUserCrudService;
		this.newUserService = newUserService;
		this.exerciseCrudService = exerciseCrudService;
		this.historyCrudService = historyCrudService;
		this.demoUserProperties = demoUserProperties;
	}
	
	@PostConstruct
	public void createDemoUser() {
		if (!demoUserProperties.isEnabled()) {
			return;
		}
		
		// create the demo user
		AppUserResponseDTO appUserResponseDTO = appUserCrudService.create(
				new AppUserRequestDTO(demoUserProperties.getName(),
						demoUserProperties.getPassword(),
						demoUserProperties.getRolename()));
		if (appUserResponseDTO == null) {
			throw new FitBuddyException("Couldn't create the demo user.");
		}
		demoUserId = appUserResponseDTO.getId();
		
		clearAndAddData();
	}
	
	@Scheduled(fixedRateString = "${fitbuddy.demo-user.reset-period}")
	public void clearAndAddData() {
		// clear all history
		for (HistoryResponseDTO historyResponseDTO : historyCrudService.readMany(demoUserId)) {
			historyCrudService.delete(historyResponseDTO.getId());
		}
		// clear all exercises
		for (ExerciseResponseDTO exerciseResponseDTO : exerciseCrudService.readMany(demoUserId)) {
			exerciseCrudService.delete(exerciseResponseDTO.getId());
		}
		
		// add default exercises
		if (demoUserProperties.isAddDefaultExercises()) {
			newUserService.addDefaultExercises(demoUserId);
		}
		
		// add history
		if (demoUserProperties.isAddHistory()) {
			List<ExerciseResponseDTO> exerciseResponseDTOs = exerciseCrudService.readMany(demoUserId);
			for (ExerciseResponseDTO exerciseResponseDTO : exerciseResponseDTOs) { 		
				historyCrudService.create(
						new HistoryRequestDTO(demoUserId,
								exerciseResponseDTO.getName(),
								1, 1,
								LocalDate.now().toString()));
			}			
		}		
	}
}
