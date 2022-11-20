package app.fitbuddy.service.operation;

import app.fitbuddy.dto.exercise.ExerciseRequestDTO;
import app.fitbuddy.entity.DefaultExercise;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.DefaultExerciseRepository;
import app.fitbuddy.service.crud.ExerciseCrudService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewUserServiceTest {
	private final static int DUMMY_USER_ID = 17;
	@InjectMocks	NewUserService newUserService;
	@Mock	DefaultExerciseRepository defaultExerciseRepository;
	@Mock	ExerciseCrudService exerciseCrudService;

	@Test
	void newUser_whenAddsDefaultExercises_shouldFindAllDefaultExercises() {
		when(defaultExerciseRepository.findAll()).thenReturn(dummyDefaultExercises());

		newUserService.addDefaultExercises(DUMMY_USER_ID);

		verify(defaultExerciseRepository).findAll();
	}

	@Test
	void newUser_whenAddsDefaultExercises_shouldCreateDtoForEachDefaultExercise() {
		when(defaultExerciseRepository.findAll()).thenReturn(dummyDefaultExercises());

		newUserService.addDefaultExercises(DUMMY_USER_ID);

		verify(exerciseCrudService, times(dummyDefaultExercises().size())).create(any());
	}

	@Test
	void newUser_whenAddsDefaultExercises_shouldCreateCorrespondingExerciseDtoFromDefaultExercises() {
		when(defaultExerciseRepository.findAll()).thenReturn(dummyDefaultExercises());
		ArgumentCaptor<ExerciseRequestDTO> requestDTOCaptor = ArgumentCaptor.forClass(ExerciseRequestDTO.class);

		newUserService.addDefaultExercises(DUMMY_USER_ID);

		verify(exerciseCrudService, times(dummyDefaultExercises().size())).create(requestDTOCaptor.capture());
		List<ExerciseRequestDTO> capturedRequestDTOs = requestDTOCaptor.getAllValues();
		dummyDefaultExercises().forEach(
				dummyDefaultExercise -> {
					assertTrue(hasCorrespondingDto(capturedRequestDTOs, dummyDefaultExercise));
				}
		);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(ints = {-7, -1, -1000001})
	void newUser_whenAppUserIsNullOrNegative_shouldThrowException(Integer appUserId) {
		assertThrows(FitBuddyException.class, () -> {
			newUserService.addDefaultExercises(appUserId);
		});
	}

	/**
	 * Checks that captured ExerciseRequestDTO list contains an exercise with expected name and appUserId DUMMY_USER_ID
	 */
	private boolean hasCorrespondingDto(List<ExerciseRequestDTO> capturedRequestDTOs,
										DefaultExercise defaultExercise) {
		return capturedRequestDTOs.stream()
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
