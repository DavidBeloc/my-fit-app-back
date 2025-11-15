package ru.yandex.myfitappback.exercise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record StrengthEditDto(@NotBlank(message = "Наименование не может быть пустым и должно содержать от 1 до 50 символов.")
                              @Size(min = 1, max = 50, message = "Наименование не может быть пустым и должно содержать от 1 до 50 символов.")
                              String name,
                              @NotNull(message = "Значение веса должно быть указано и иметь положительное число.")
                              @Positive(message = "Значение веса должно быть указано и иметь положительное число.")
                              Double weight,
                              @NotNull(message = "Значение повторений должно быть указано и иметь положительное число.")
                              @Positive(message = "Значение повторений должно быть указано и иметь положительное число.")
                              Short repeat,
                              @Size(max = 128, message = "Отчёт должен содержать от 1 до 128 символов.")
                              String report) {
}
