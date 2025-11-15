package ru.yandex.myfitappback.exercise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.myfitappback.exercise.dto.*;
import ru.yandex.myfitappback.exercise.service.ExerciseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/workouts/{workoutId}/exercises")
@RequiredArgsConstructor
@Slf4j
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseReadDto> findExercises(@PathVariable Long workoutId) {
        log.info("Поиск упражнений для workout с id '{}'.", workoutId);
        return exerciseService.findExercisesByWorkoutId(workoutId);
    }

    @GetMapping("/{exerciseId}")
    public ExerciseReadDto findExerciseById(@PathVariable Long exerciseId) {
        log.info("Поиск упражнения с id '{}'.", exerciseId);
        return exerciseService.findExerciseById(exerciseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseReadDto createExercise(@PathVariable Long workoutId,
                                          @RequestBody ExerciseCreateDto exerciseCreateDto) {
        log.info("Создание упражнения для workout с id '{}'.", workoutId);
        return exerciseService.createExercise(workoutId, exerciseCreateDto);
    }

    @PatchMapping("/{exerciseId}")
    public ExerciseReadDto updateExercise(@PathVariable Long exerciseId,
                                          @RequestBody ExerciseEditDto exerciseEditDto) {
        log.info("Обновление упражнения с id '{}'.", exerciseId);
        return exerciseService.updateExercise(exerciseId, exerciseEditDto);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long exerciseId) {
        log.info("Удаление упражнения с id '{}'.", exerciseId);
        return exerciseService.deleteExercise(exerciseId) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{exerciseId}/cardio")
    public CardioReadDto findCardioByExerciseId(@PathVariable Long exerciseId) {
        log.info("Поиск кардио-упражнения для exercise с id '{}'.", exerciseId);
        return exerciseService.findCardioByExerciseId(exerciseId);
    }

    @PostMapping("/{exerciseId}/cardio")
    @ResponseStatus(HttpStatus.CREATED)
    public CardioReadDto createCardio(@PathVariable Long exerciseId,
                                      @RequestBody @Valid CardioCreateDto cardioCreateDto) {
        log.info("Создание кардио-упражнения для exercise с id '{}'.", exerciseId);
        return exerciseService.createCardio(exerciseId, cardioCreateDto);
    }

    @PatchMapping("/{exerciseId}/cardio/{cardioId}")
    public CardioReadDto updateCardio(@PathVariable Long cardioId,
                                      @RequestBody @Valid CardioEditDto cardioEditDto) {
        log.info("Обновление кардио-упражнения с id '{}'.", cardioId);
        return exerciseService.updateCardio(cardioId, cardioEditDto);
    }

    @GetMapping("/{exerciseId}/calisthenics")
    public CalisthenicsReadDto findCalisthenicsByExerciseId(@PathVariable Long exerciseId) {
        log.info("Поиск упражнения-калистеника для exercise с id '{}'.", exerciseId);
        return exerciseService.findCalisthenicsByExerciseId(exerciseId);
    }

    @PostMapping("/{exerciseId}/calisthenics")
    @ResponseStatus(HttpStatus.CREATED)
    public CalisthenicsReadDto createCalisthenics(@PathVariable Long exerciseId,
                                                  @RequestBody @Valid CalisthenicsCreateDto calisthenicsCreateDto) {
        log.info("Создание упражнения-калистеника для exercise с id '{}'.", exerciseId);
        return exerciseService.createCalisthenics(exerciseId, calisthenicsCreateDto);
    }

    @PatchMapping("/{exerciseId}/calisthenics/{calisthenicsId}")
    public CalisthenicsReadDto updateCalisthenics(@PathVariable Long calisthenicsId,
                                                  @RequestBody @Valid CalisthenicsEditDto calisthenicsEditDto) {
        log.info("Обновление упражнения-калистеника с id '{}'.", calisthenicsId);
        return exerciseService.updateCalisthenics(calisthenicsId, calisthenicsEditDto);
    }

    @GetMapping("/{exerciseId}/strength")
    public StrengthReadDto findStrengthByExerciseId(@PathVariable Long exerciseId) {
        log.info("Поиск силового-упражнения для exercise с id '{}'.", exerciseId);
        return exerciseService.findStrengthByExerciseId(exerciseId);
    }

    @PostMapping("/{exerciseId}/strength")
    @ResponseStatus(HttpStatus.CREATED)
    public StrengthReadDto createStrength(@PathVariable Long exerciseId,
                                          @RequestBody @Valid StrengthCreateDto strengthCreateDto) {
        log.info("Создание силового-упражнения для exercise с id '{}'.", exerciseId);
        return exerciseService.createStrength(exerciseId, strengthCreateDto);
    }

    @PatchMapping("/{exerciseId}/strength/{strengthId}")
    public StrengthReadDto updateStrength(@PathVariable Long strengthId,
                                          @RequestBody @Valid StrengthEditDto strengthEditDto) {
        log.info("Обновление силового-упражнения с id '{}'.", strengthId);
        return exerciseService.updateStrength(strengthId, strengthEditDto);
    }
}














