package ru.yandex.myfitappback.nutrition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.myfitappback.nutrition.dto.NutritionCreateDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionEditDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionReadDto;
import ru.yandex.myfitappback.nutrition.service.NutritionService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users/{userId}/nutritions")
@RequiredArgsConstructor
@Slf4j
public class NutritionController {

    private final NutritionService nutritionService;

    @GetMapping
    public NutritionReadDto findNutritionByDate(@PathVariable Long userId,
                                                @RequestParam LocalDate date) {
        log.info("Поиск питания с датой '{}' для пользователя с id '{}'.", date, userId);
        return nutritionService.findNutritionByDate(userId, date)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NutritionReadDto createNutrition(@PathVariable Long userId,
                                            @RequestBody @Valid NutritionCreateDto nutritionCreateDto) {
        log.info("Создание питания для пользователя с id '{}'.", userId);
        return nutritionService.createNutrition(userId, nutritionCreateDto);
    }

    @PatchMapping("/{nutritionId}")
    public NutritionReadDto updateNutrition(@PathVariable Long nutritionId,
                                            @RequestBody @Valid NutritionEditDto nutritionEditDto) {
        log.info("Обновление питания с id '{}'.", nutritionId);
        return nutritionService.updateNutrition(nutritionId, nutritionEditDto);
    }

    @DeleteMapping("/{nutritionId}")
    public ResponseEntity<?> deleteNutrition(@PathVariable Long nutritionId) {
        log.info("Удаление питания с id '{}'.", nutritionId);
        return nutritionService.deleteNutrition(nutritionId) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
