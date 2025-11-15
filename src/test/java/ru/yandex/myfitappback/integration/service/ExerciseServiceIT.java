package ru.yandex.myfitappback.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.yandex.myfitappback.exercise.dto.*;
import ru.yandex.myfitappback.exercise.model.ExerciseType;
import ru.yandex.myfitappback.exercise.service.ExerciseService;
import ru.yandex.myfitappback.integration.IntegrationTestBase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class ExerciseServiceIT extends IntegrationTestBase {

    private final ExerciseService exerciseService;

    @Test
    void findExercisesByWorkoutId() {
        List<ExerciseReadDto> actualResult = exerciseService.findExercisesByWorkoutId(1L);
        assertEquals(4, actualResult.size());
        assertEquals(ExerciseType.CARDIO, actualResult.get(0).exerciseType());
        assertEquals(ExerciseType.CALISTHENICS, actualResult.get(1).exerciseType());
    }

    @Test
    void findExerciseById() {
        ExerciseReadDto expectedResult = new ExerciseReadDto(1L, ExerciseType.CARDIO);
        ExerciseReadDto actualResult = exerciseService.findExerciseById(1L);
        assertEquals(expectedResult.id(), actualResult.id());
        assertEquals(expectedResult.exerciseType(), actualResult.exerciseType());
    }

    @Test
    void createExercise() {
        ExerciseCreateDto exerciseCreateDto = new ExerciseCreateDto(ExerciseType.STRENGTH);
        ExerciseReadDto actualResult = exerciseService.createExercise(4L, exerciseCreateDto);
        assertThat(actualResult.id()).isNotNull();
        assertEquals(ExerciseType.STRENGTH, actualResult.exerciseType());
    }

    @Test
    void updateExercise() {
        ExerciseEditDto exerciseEditDto = new ExerciseEditDto(ExerciseType.CALISTHENICS);
        ExerciseReadDto actualResult = exerciseService.updateExercise(5L, exerciseEditDto);
        assertEquals(5L, actualResult.id());
        assertEquals(ExerciseType.CALISTHENICS, actualResult.exerciseType());
    }

    @Nested
    @Tag("deleteExercise")
    class DeleteExerciseTest {

        @Test
        void deleteExercise() {
            boolean actualResult = exerciseService.deleteExercise(1L);
            assertTrue(actualResult);
        }

        @Test
        void deleteIsFailIfExerciseDoesNotExist() {
            boolean actualResult = exerciseService.deleteExercise(20L);
            assertFalse(actualResult);
        }
    }

    @Test
    void findCardioByExerciseId() {
        CardioReadDto expectedResult = new CardioReadDto(1L, "Бег с уклоном", (short) 2500, "Отчет по пробежке");
        CardioReadDto actualResult = exerciseService.findCardioByExerciseId(1L);
        assertEquals(expectedResult.id(), actualResult.id());
        assertEquals(expectedResult.name(), actualResult.name());
        assertEquals(expectedResult.distance(), actualResult.distance());
        assertEquals(expectedResult.report(), actualResult.report());
    }

    @Nested
    @Tag("createCardio")
    class CreateCardioTest {

        private final CardioCreateDto cardioCreateDto = new CardioCreateDto("Велосипед", (short) 7200, "Отчёт по упражнению");

        @Test
        void createCardio() {
            CardioReadDto actualResult = exerciseService.createCardio(6L, cardioCreateDto);
            assertThat(actualResult.id()).isNotNull();
            assertEquals(cardioCreateDto.name(), actualResult.name());
            assertEquals(cardioCreateDto.distance(), actualResult.distance());
            assertEquals(cardioCreateDto.report(), actualResult.report());
        }

        @Test
        void throwExceptionIfExerciseTypeIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> exerciseService.createCardio(2L, cardioCreateDto));
        }
    }

    @Test
    void updateCardio() {
        CardioEditDto cardioEditDto = new CardioEditDto("Беговая дорожка", (short) 4200, "Отчёт по упражнению");
        CardioReadDto actualResult = exerciseService.updateCardio(1L, cardioEditDto);
        assertEquals(1L, actualResult.id());
        assertEquals(cardioEditDto.name(), actualResult.name());
        assertEquals(cardioEditDto.distance(), actualResult.distance());
        assertEquals(cardioEditDto.report(), actualResult.report());
    }

    @Test
    void deleteCardio() {
        exerciseService.deleteExercise(1L);
        assertThrows(RuntimeException.class, () -> exerciseService.findCardioByExerciseId(1L));
    }

    @Test
    void findCalisthenicsByExerciseId() {
        CalisthenicsReadDto expectedResult = new CalisthenicsReadDto(1L, "Отжимания", (short) 25, "Отчет по упражнению");
        CalisthenicsReadDto actualResult = exerciseService.findCalisthenicsByExerciseId(2L);
        assertEquals(expectedResult.id(), actualResult.id());
        assertEquals(expectedResult.name(), actualResult.name());
        assertEquals(expectedResult.repeat(), actualResult.repeat());
        assertEquals(expectedResult.report(), actualResult.report());
    }

    @Nested
    @Tag("createCalisthenics")
    class CreateCalisthenicsTest {

        private final CalisthenicsCreateDto calisthenicsCreateDto = new CalisthenicsCreateDto("Приседания", (short) 30, "Отчёт по приседаниям");

        @Test
        void createCalisthenics() {
            CalisthenicsReadDto actualResult = exerciseService.createCalisthenics(8L, calisthenicsCreateDto);
            assertThat(actualResult.id()).isNotNull();
            assertEquals(calisthenicsCreateDto.name(), actualResult.name());
            assertEquals(calisthenicsCreateDto.repeat(), actualResult.repeat());
            assertEquals(calisthenicsCreateDto.report(), actualResult.report());
        }

        @Test
        void throwExceptionIfExerciseTypeIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> exerciseService.createCalisthenics(3L, calisthenicsCreateDto));
        }
    }

    @Test
    void updateCalisthenics() {
        CalisthenicsEditDto calisthenicsEditDto = new CalisthenicsEditDto("Отжимания, обновление", (short) 35, "Обновленный отчёт по отжиманиям");
        CalisthenicsReadDto actualResult = exerciseService.updateCalisthenics(1L, calisthenicsEditDto);
        assertEquals(1L, actualResult.id());
        assertEquals(calisthenicsEditDto.name(), actualResult.name());
        assertEquals(calisthenicsEditDto.repeat(), actualResult.repeat());
        assertEquals(calisthenicsEditDto.report(), actualResult.report());
    }

    @Test
    void deleteCalisthenics() {
        exerciseService.deleteExercise(2L);
        assertThrows(RuntimeException.class, () -> exerciseService.findCalisthenicsByExerciseId(2L));
    }

    @Test
    void findStrengthByExerciseId() {
        StrengthReadDto expectedResult = new StrengthReadDto(1L, "Жим лежа", 37.5, (short) 10, "Отчет по упражнению");
        StrengthReadDto actualResult = exerciseService.findStrengthByExerciseId(3L);
        assertEquals(expectedResult.id(), actualResult.id());
        assertEquals(expectedResult.name(), actualResult.name());
        assertEquals(expectedResult.weight(), actualResult.weight());
        assertEquals(expectedResult.repeat(), actualResult.repeat());
        assertEquals(expectedResult.report(), actualResult.report());
    }

    @Nested
    @Tag("createStrength")
    class CreateStrengthTest {

        private final StrengthCreateDto strengthCreateDto = new StrengthCreateDto("Подьём штанги на спину", 25.0, (short) 30, "Отчёт по упражнению");

        @Test
        void createStrength() {
            StrengthReadDto actualResult = exerciseService.createStrength(4L, strengthCreateDto);
            assertThat(actualResult.id()).isNotNull();
            assertEquals(strengthCreateDto.name(), actualResult.name());
            assertEquals(strengthCreateDto.weight(), actualResult.weight());
            assertEquals(strengthCreateDto.repeat(), actualResult.repeat());
            assertEquals(strengthCreateDto.report(), actualResult.report());
        }

        @Test
        void throwExceptionIfExerciseTypeIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> exerciseService.createStrength(6L, strengthCreateDto));
        }
    }

    @Test
    void updateStrength() {
        StrengthEditDto strengthEditDto = new StrengthEditDto("Жим лежа, обновление", 40.0, (short) 11, "Обновленный отчет по упражнению");
        StrengthReadDto actualResult = exerciseService.updateStrength(1L, strengthEditDto);
        assertEquals(1L, actualResult.id());
        assertEquals(strengthEditDto.name(), actualResult.name());
        assertEquals(strengthEditDto.weight(), actualResult.weight());
        assertEquals(strengthEditDto.repeat(), actualResult.repeat());
        assertEquals(strengthEditDto.report(), actualResult.report());
    }

    @Test
    void deleteStrength() {
        exerciseService.deleteExercise(3L);
        assertThrows(RuntimeException.class, () -> exerciseService.findStrengthByExerciseId(3L));
    }
}