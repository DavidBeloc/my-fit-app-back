package ru.yandex.myfitappback.workout.dto;

import ru.yandex.myfitappback.exercise.dto.ExerciseReadDto;
import ru.yandex.myfitappback.exercise.model.Exercise;

import java.time.LocalDate;
import java.util.List;

public record WorkoutReadDto(Long id,
                             LocalDate date,
                             List<ExerciseReadDto> exercises) {
}
