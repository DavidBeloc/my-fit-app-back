package ru.yandex.myfitappback.nutrition.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record NutritionCreateDto(LocalDate date,
                                 @PositiveOrZero(message = "Значение количества протеинов должно быть положительным от 0.")
                                 Short proteins,
                                 @PositiveOrZero(message = "Значение количества углеводов должно быть положительным от 0.")
                                 Short carbohydrates,
                                 @PositiveOrZero(message = "Значение количества жиров должно быть положительным от 0.")
                                 Short fats) {
}
