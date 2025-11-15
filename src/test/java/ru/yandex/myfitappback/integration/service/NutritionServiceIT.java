package ru.yandex.myfitappback.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.yandex.myfitappback.integration.IntegrationTestBase;
import ru.yandex.myfitappback.nutrition.dto.NutritionCreateDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionEditDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionReadDto;
import ru.yandex.myfitappback.nutrition.service.NutritionService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class NutritionServiceIT extends IntegrationTestBase {

    private final NutritionService nutritionService;

    private static final NutritionReadDto savedNutrition1 = new NutritionReadDto(
            1L,
            LocalDate.of(2025, 1, 1),
            (short) 110,
            (short) 400,
            (short) 20);

    @Test
    void findNutritionByDate() {
        Optional<NutritionReadDto> actualResult = nutritionService.findNutritionByDate(1L, LocalDate.of(2025, 1, 1));
        assertTrue(actualResult.isPresent());
        assertThat(actualResult.get()).isEqualTo(savedNutrition1);
    }

    @Test
    void createNutrition() {
        NutritionCreateDto nutritionCreateDto = new NutritionCreateDto(LocalDate.of(2025, 2, 1),
                (short) 60, (short) 250, (short) 50);
        NutritionReadDto actualResult = nutritionService.createNutrition(2L, nutritionCreateDto);
        assertThat(actualResult.id()).isNotNull();
        assertEquals(nutritionCreateDto.date(), actualResult.date());
        assertEquals(nutritionCreateDto.proteins(), actualResult.proteins());
        assertEquals(nutritionCreateDto.carbohydrates(), actualResult.carbohydrates());
        assertEquals(nutritionCreateDto.fats(), actualResult.fats());
    }

    @Test
    void updateNutrition() {
        NutritionEditDto nutritionEditDto = new NutritionEditDto(LocalDate.of(2025, 2, 1),
                (short) 60, (short) 250, (short) 50);
        NutritionReadDto actualResult = nutritionService.updateNutrition(5L, nutritionEditDto);
        assertEquals(5L, actualResult.id());
        assertEquals(nutritionEditDto.date(), actualResult.date());
        assertEquals(nutritionEditDto.proteins(), actualResult.proteins());
        assertEquals(nutritionEditDto.carbohydrates(), actualResult.carbohydrates());
        assertEquals(nutritionEditDto.fats(), actualResult.fats());
    }

    @Nested
    @Tag("deleteNutrition")
    class DeleteNutritionTest {

        @Test
        void deleteNutrition() {
            boolean actualResult = nutritionService.deleteNutrition(1L);
            assertTrue(actualResult);
        }

        @Test
        void deleteIsFailIfNutritionDoesNotExist() {
            boolean actualResult = nutritionService.deleteNutrition(10L);
            assertFalse(actualResult);
        }
    }
}