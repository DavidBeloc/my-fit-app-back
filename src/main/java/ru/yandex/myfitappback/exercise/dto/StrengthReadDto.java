package ru.yandex.myfitappback.exercise.dto;

public record StrengthReadDto(Long id,
                              String name,
                              Double weight,
                              Short repeat,
                              String report) {
}
