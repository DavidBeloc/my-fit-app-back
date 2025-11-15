package ru.yandex.myfitappback.workout.service;

import ru.yandex.myfitappback.util.PagedResponse;
import ru.yandex.myfitappback.workout.dto.WorkoutEditDto;
import ru.yandex.myfitappback.workout.dto.WorkoutReadDto;
import ru.yandex.myfitappback.workout.dto.WorkoutCreateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {

    WorkoutReadDto findWorkoutById(Long workoutId);

    PagedResponse<WorkoutReadDto> findWorkoutsByUserId(Long userId, Integer page, Integer size);

    List<WorkoutReadDto> findWorkoutsByDate(Long userId, LocalDate date);

    WorkoutReadDto createWorkout(Long userId, WorkoutCreateDto workoutCreateDto);

    WorkoutReadDto updateWorkout(Long workoutId, WorkoutEditDto workoutEditDto);

    boolean deleteWorkout(Long workoutId);
}
