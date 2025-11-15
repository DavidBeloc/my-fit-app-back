package ru.yandex.myfitappback.workout.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.myfitappback.date.repository.DateRepository;
import ru.yandex.myfitappback.util.PagedResponse;
import ru.yandex.myfitappback.workout.dto.WorkoutCreateDto;
import ru.yandex.myfitappback.workout.dto.WorkoutEditDto;
import ru.yandex.myfitappback.workout.dto.WorkoutReadDto;
import ru.yandex.myfitappback.workout.mapper.WorkoutMapper;
import ru.yandex.myfitappback.workout.model.Workout;
import ru.yandex.myfitappback.workout.repository.WorkoutRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final DateRepository dateRepository;

    private final WorkoutMapper workoutMapper;

    @Override
    public WorkoutReadDto findWorkoutById(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .map(workout -> {
                    log.debug("Workout с id '{}' найдено.", workoutId);
                    return workoutMapper.workoutToWorkoutDto(workout);
                }).orElseThrow();
    }

    @Override
    public PagedResponse<WorkoutReadDto> findWorkoutsByUserId(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        var workouts = workoutRepository.findAllByUserId(userId, pageable);
        var workoutReadDto = workouts.getContent().stream()
                .map(workoutMapper::workoutToWorkoutDto).toList();
        log.debug("Получен список workouts для пользователя с id '{}'.", userId);
        return new PagedResponse<>(workoutReadDto, new PagedResponse.Metadata(
                workouts.getNumber(),
                workouts.getSize(),
                workouts.getTotalElements(),
                workouts.getTotalPages(),
                workouts.isLast()));
    }

    @Override
    public List<WorkoutReadDto> findWorkoutsByDate(Long userId, LocalDate date) {
        return dateRepository.findByUserIdAndDate(userId, date)
                .map(foundDate -> workoutRepository.findAllByDateId(foundDate.getId()))
                .map(workouts -> {
                    log.debug("Получен список workouts для даты '{}' пользователя с id '{}'.", date, userId);
                    return workoutMapper.workoutsToWorkoutsDto(workouts);
                }).orElseThrow();
    }

    @Override
    public WorkoutReadDto createWorkout(Long userId, WorkoutCreateDto workoutCreateDto) {
        return dateRepository.findByUserIdAndDate(userId, workoutCreateDto.date())
                .map(date -> {
                    Workout newWorkout = workoutMapper.workoutCreateDtoToWorkout(workoutCreateDto, date);
                    Workout savedWorkout = workoutRepository.save(newWorkout);
                    log.debug("Создано workout для пользователя с id '{}'.", userId);
                    return workoutMapper.workoutToWorkoutDto(savedWorkout);
                }).orElseThrow();
    }

    @Override
    public WorkoutReadDto updateWorkout(Long workoutId, WorkoutEditDto workoutEditDto) {
        Workout workoutToUpdate = workoutRepository.findById(workoutId)
                .map(workout -> {
                    Long userId = workout.getDate().getUser().getId();
                    return dateRepository.findByUserIdAndDate(userId, workoutEditDto.date())
                            .map(date -> workoutMapper.workoutEditDtoToWorkout(workoutEditDto, date, workout.getExercises(), workoutId))
                            .orElseThrow();
                }).orElseThrow();
        Workout savedWorkout = workoutRepository.saveAndFlush(workoutToUpdate);
        log.debug("Обновлено workout с id '{}'.", workoutId);
        return workoutMapper.workoutToWorkoutDto(savedWorkout);
    }

    @Override
    public boolean deleteWorkout(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .map(workout -> {
                    workoutRepository.delete(workout);
                    workoutRepository.flush();
                    log.debug("Удалено workout с id '{}'.", workoutId);
                    return true;
                }).orElse(false);
    }
}