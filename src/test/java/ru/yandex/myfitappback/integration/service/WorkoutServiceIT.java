package ru.yandex.myfitappback.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.yandex.myfitappback.exercise.dto.ExerciseReadDto;
import ru.yandex.myfitappback.exercise.model.Exercise;
import ru.yandex.myfitappback.exercise.model.ExerciseType;
import ru.yandex.myfitappback.integration.IntegrationTestBase;
import ru.yandex.myfitappback.util.PagedResponse;
import ru.yandex.myfitappback.workout.dto.WorkoutCreateDto;
import ru.yandex.myfitappback.workout.dto.WorkoutEditDto;
import ru.yandex.myfitappback.workout.dto.WorkoutReadDto;
import ru.yandex.myfitappback.workout.service.WorkoutService;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class WorkoutServiceIT extends IntegrationTestBase {

    private final WorkoutService workoutService;

    private static final WorkoutReadDto savedWorkout1 = new WorkoutReadDto(
            4L,
            LocalDate.of(2025, 2, 2),
            new ArrayList<>(List.of(new ExerciseReadDto(8L,ExerciseType.CALISTHENICS))));

    @Test
    void findWorkoutById() {
        WorkoutReadDto actualResult = workoutService.findWorkoutById(4L);
        assertThat(actualResult).isEqualTo(savedWorkout1);
    }

    @Test
    void findWorkoutsByUserId() {
        PagedResponse<WorkoutReadDto> actualResult = workoutService.findWorkoutsByUserId(1L, 0, 3);
        assertEquals(3, actualResult.content().size());
        assertEquals(1L, actualResult.content().get(0).id());
        assertEquals(5, actualResult.metadata().totalElements());
    }

    @Test
    void findWorkoutsByDate() {
        List<WorkoutReadDto> actualResult = workoutService.findWorkoutsByDate(1L, LocalDate.of(2025, 2, 2));
        assertEquals(1, actualResult.size());
        assertEquals(savedWorkout1.id(), actualResult.get(0).id());
        assertEquals(savedWorkout1.date(), actualResult.get(0).date());
        assertEquals(savedWorkout1.exercises().get(0), actualResult.get(0).exercises().get(0));
    }

    @Test
    void createWorkout() {
        WorkoutCreateDto workoutCreateDto = new WorkoutCreateDto(LocalDate.of(2025, 1, 2));
        WorkoutReadDto actualResult = workoutService.createWorkout(1L, workoutCreateDto);
        assertThat(actualResult.id()).isNotNull();
        assertEquals(workoutCreateDto.date(), actualResult.date());
    }

    @Test
    void updateWorkout() {
        WorkoutEditDto workoutEditDto = new WorkoutEditDto(LocalDate.of(2025, 1, 2));
        WorkoutReadDto actualResult = workoutService.updateWorkout(1L, workoutEditDto);
        assertEquals(1L, actualResult.id());
        assertEquals(workoutEditDto.date(), actualResult.date());
    }

    @Nested
    @Tag("deleteWorkout")
    class DeleteWorkoutTest {

        @Test
        void deleteWorkout() {
            boolean actualResult = workoutService.deleteWorkout(1L);
            assertTrue(actualResult);
        }

        @Test
        void deleteIsFailIfWorkoutDoesNotExist() {
            boolean actualResult = workoutService.deleteWorkout(10L);
            assertFalse(actualResult);
        }
    }
}