package ru.yandex.myfitappback.nutrition.dto;

import java.time.LocalDate;

public record NutritionReadDto(Long id,
                               LocalDate date,
                               Short proteins,
                               Short carbohydrates,
                               Short fats) {
}
