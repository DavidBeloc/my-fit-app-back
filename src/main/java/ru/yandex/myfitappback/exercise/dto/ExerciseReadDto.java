package ru.yandex.myfitappback.exercise.dto;

import ru.yandex.myfitappback.exercise.model.ExerciseType;

public record ExerciseReadDto(Long id,
                              ExerciseType exerciseType) {
}
