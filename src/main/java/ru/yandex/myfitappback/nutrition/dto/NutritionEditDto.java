package ru.yandex.myfitappback.nutrition.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record NutritionEditDto(LocalDate date,
                               @PositiveOrZero Short proteins,
                               @PositiveOrZero Short carbohydrates,
                               @PositiveOrZero Short fats) {
}
