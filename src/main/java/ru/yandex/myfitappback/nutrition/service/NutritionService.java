package ru.yandex.myfitappback.nutrition.service;

import ru.yandex.myfitappback.nutrition.dto.NutritionCreateDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionEditDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionReadDto;

import java.time.LocalDate;
import java.util.Optional;

public interface NutritionService {

    Optional<NutritionReadDto> findNutritionByDate(Long userId, LocalDate date);

    NutritionReadDto createNutrition(Long userId, NutritionCreateDto nutritionCreateDto);

    NutritionReadDto updateNutrition(Long nutritionId, NutritionEditDto nutritionEditDto);

    boolean deleteNutrition(Long nutritionId);
}
