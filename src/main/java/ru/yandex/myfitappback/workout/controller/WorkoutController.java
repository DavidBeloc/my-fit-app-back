package ru.yandex.myfitappback.workout.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.myfitappback.util.PagedResponse;
import ru.yandex.myfitappback.workout.dto.WorkoutEditDto;
import ru.yandex.myfitappback.workout.dto.WorkoutReadDto;
import ru.yandex.myfitappback.workout.dto.WorkoutCreateDto;
import ru.yandex.myfitappback.workout.service.WorkoutService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/workouts")
@RequiredArgsConstructor
@Slf4j
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping("/{workoutId}")
    public WorkoutReadDto findWorkoutById(@PathVariable Long workoutId) {
        log.info("Поиск workout с id '{}'.", workoutId);
        return workoutService.findWorkoutById(workoutId);
    }

    @GetMapping("/by-date")
    public List<WorkoutReadDto> findWorkoutsByDate(@PathVariable Long userId,
                                                   @RequestParam LocalDate date) {
        log.info("Поиск workouts с датой '{}' для пользователя с id '{}'.", date, userId);
        return workoutService.findWorkoutsByDate(userId, date);
    }

    @GetMapping
    public PagedResponse<WorkoutReadDto> findWorkouts(@PathVariable Long userId,
                                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Поиск всех workouts для пользователя с id '{}'.", userId);
        return workoutService.findWorkoutsByUserId(userId, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutReadDto createWorkout(@PathVariable Long userId,
                                        @RequestBody WorkoutCreateDto workoutCreateDto) {
        log.info("Создание workout для пользователя с id '{}'.", userId);
        return workoutService.createWorkout(userId, workoutCreateDto);
    }

    @PatchMapping("/{workoutId}")
    public WorkoutReadDto updateWorkout(@PathVariable Long workoutId,
                                        @RequestBody WorkoutEditDto workoutEditDto) {
        log.info("Обновление workout с id '{}'.", workoutId);
        return workoutService.updateWorkout(workoutId, workoutEditDto);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<?> deleteNutrition(@PathVariable Long workoutId) {
        log.info("Удаление workout с id '{}'.", workoutId);
        return workoutService.deleteWorkout(workoutId) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
