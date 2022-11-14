package app.fitbuddy.operation.service;

import app.fitbuddy.dto.ExerciseDto;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.jpa.entity.DefaultExercise;
import app.fitbuddy.jpa.repository.DefaultExerciseCrudRepository;
import app.fitbuddy.jpa.service.crud.ExerciseCrudService;
import app.fitbuddy.testhelper.DefaultExerciseTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewUserServiceTest {
	private final static int DUMMY_USER_ID = 17;
	@InjectMocks	NewUserService newUserService;
	@Mock	DefaultExerciseCrudRepository defaultExerciseCrudRepository;
	@Mock	ExerciseCrudService exerciseCrudService;

	@Test
	public void newUser_whenAddsDefaultExercises_shouldFindAllDefaultExercises() {
		when(defaultExerciseCrudRepository.findAll()).thenReturn(dummyDefaultExercises());

		newUserService.addDefaultExercises(DUMMY_USER_ID);

		verify(defaultExerciseCrudRepository).findAll();
	}

	@Test
	public void newUser_whenAddsDefaultExercises_shouldCreateDtoForEachDefaultExercise() {
		when(defaultExerciseCrudRepository.findAll()).thenReturn(dummyDefaultExercises());

		newUserService.addDefaultExercises(DUMMY_USER_ID);

		verify(exerciseCrudService, times(dummyDefaultExercises().size())).create(any());
	}

	@Test
	public void newUser_whenAddsDefaultExercises_shouldCreateCorrespondingExerciseDtoFromDefaultExercises() {
		when(defaultExerciseCrudRepository.findAll()).thenReturn(dummyDefaultExercises());
		ArgumentCaptor<ExerciseDto> exerciseDtoCaptor = ArgumentCaptor.forClass(ExerciseDto.class);

		newUserService.addDefaultExercises(DUMMY_USER_ID);

		verify(exerciseCrudService, times(dummyDefaultExercises().size())).create(exerciseDtoCaptor.capture());
		List<ExerciseDto> capturedExerciseDto = exerciseDtoCaptor.getAllValues();
		dummyDefaultExercises().forEach(
				dummyDefaultExercise -> {
					assertTrue(hasCorrespondingDto(capturedExerciseDto, dummyDefaultExercise));
				}
		);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(ints = {-7, -1, -1000001})
	public void newUser_whenAppUserIsNullOrNegative_shouldThrowException(Integer appUserId) {
		assertThrows(FitBuddyException.class, () -> {
			newUserService.addDefaultExercises(appUserId);
		});
	}

	/**
	 * Checks that captured ExerciseDto list contains an exercise with expected name and appUserId DUMMY_USER_ID
	 */
	private boolean hasCorrespondingDto(List<ExerciseDto> capturedExerciseDto,
										DefaultExercise defaultExercise) {
		return capturedExerciseDto.stream()
				.anyMatch(dto -> DefaultExerciseTestHelper.isEqual(dto, defaultExercise) &&
						dto.getAppUserId().equals(DUMMY_USER_ID));
	}

	private List<DefaultExercise> dummyDefaultExercises() {
		return List.of(
				DefaultExerciseTestHelper.getMockDefaultExercise(17, "walk out and squats"),
				DefaultExerciseTestHelper.getMockDefaultExercise(88, "plank"),
				DefaultExerciseTestHelper.getMockDefaultExercise(7, "push ups")
		);
	}

}
