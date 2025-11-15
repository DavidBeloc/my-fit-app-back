package ru.yandex.myfitappback.exercise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.myfitappback.exercise.dto.*;
import ru.yandex.myfitappback.exercise.mapper.CalisthenicsMapper;
import ru.yandex.myfitappback.exercise.mapper.CardioMapper;
import ru.yandex.myfitappback.exercise.mapper.ExerciseMapper;
import ru.yandex.myfitappback.exercise.mapper.StrengthMapper;
import ru.yandex.myfitappback.exercise.model.*;
import ru.yandex.myfitappback.exercise.repository.CalisthenicsRepository;
import ru.yandex.myfitappback.exercise.repository.CardioRepository;
import ru.yandex.myfitappback.exercise.repository.ExerciseRepository;
import ru.yandex.myfitappback.exercise.repository.StrengthRepository;
import ru.yandex.myfitappback.workout.repository.WorkoutRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final WorkoutRepository workoutRepository;

    private final CardioRepository cardioRepository;

    private final CalisthenicsRepository calisthenicsRepository;

    private final StrengthRepository strengthRepository;

    private final ExerciseMapper exerciseMapper;

    private final CardioMapper cardioMapper;

    private final CalisthenicsMapper calisthenicsMapper;

    private final StrengthMapper strengthMapper;

    @Override
    public List<ExerciseReadDto> findExercisesByWorkoutId(Long workoutId) {
        List<Exercise> exercises = exerciseRepository.findAllByWorkoutId(workoutId);
        log.debug("Силовые-упражнения для workout с id '{}' найдены.", workoutId);
        return exerciseMapper.exercisesToExercisesReadDto(exercises);
    }

    @Override
    public ExerciseReadDto findExerciseById(Long exerciseId) {
        return exerciseRepository.findById(exerciseId)
                .map(exercise -> {
                    log.debug("Упражнение с id '{}' найдено.", exerciseId);
                    return exerciseMapper.exerciseToExerciseReadDto(exercise);
                }).orElseThrow();
    }

    @Override
    public ExerciseReadDto createExercise(Long workoutId, ExerciseCreateDto exerciseCreateDto) {
        return workoutRepository.findById(workoutId)
                .map(workout -> {
                    Exercise newExercise = exerciseMapper.exerciseCreateDtoToExercise(workout, exerciseCreateDto);
                    Exercise savedExercise = exerciseRepository.save(newExercise);
                    log.debug("Упражнение с id '{}' создано.", savedExercise.getId());
                    return exerciseMapper.exerciseToExerciseReadDto(savedExercise);
                }).orElseThrow();
    }

    @Override
    public ExerciseReadDto updateExercise(Long exerciseId, ExerciseEditDto exerciseEditDto) {
        Exercise exerciseToUpdate = exerciseRepository.findById(exerciseId)
                .map(exercise -> exerciseMapper.exerciseEditDtoToExercise(exerciseEditDto, exercise.getWorkout(), exerciseId))
                .orElseThrow();
        Exercise savedExercise = exerciseRepository.saveAndFlush(exerciseToUpdate);
        log.debug("Упражнение с id '{}' обновлено.", savedExercise.getId());
        return exerciseMapper.exerciseToExerciseReadDto(savedExercise);
    }

    @Override
    public boolean deleteExercise(Long exerciseId) {
        return exerciseRepository.findById(exerciseId)
                .map(exercise -> {
                    exerciseRepository.delete(exercise);
                    exerciseRepository.flush();
                    log.debug("Упражнение с id '{}' удалено.", exerciseId);
                    return true;
                }).orElse(false);
    }

    @Override
    public CardioReadDto findCardioByExerciseId(Long exerciseId) {
        return cardioRepository.findCardioByExerciseId(exerciseId)
                .map(cardio -> {
                    log.debug("Кардио-упражнение с id '{}' для exercise с id '{}' найдено.", cardio.getId(), exerciseId);
                    return cardioMapper.cardioToCardioReadDto(cardio);
                }).orElseThrow();
    }

    @Override
    public CardioReadDto createCardio(Long exerciseId, CardioCreateDto cardioCreateDto) {
        return exerciseRepository.findById(exerciseId)
                .map(exercise -> {
                    if (exercise.getExerciseType().equals(ExerciseType.CARDIO)) {
                        Cardio newCardio = cardioMapper.cardioCreateDtoToCardio(cardioCreateDto, exercise);
                        Cardio savedCardio = cardioRepository.save(newCardio);
                        log.debug("Кардио-упражнение с id '{}' для exercise с id '{}' создано.", savedCardio.getId(), exerciseId);
                        return cardioMapper.cardioToCardioReadDto(savedCardio);
                    }
                    throw new IllegalArgumentException();
                }).orElseThrow();
    }

    @Override
    public CardioReadDto updateCardio(Long cardioId, CardioEditDto cardioEditDto) {
        Cardio cardioToUpdate = cardioRepository.findById(cardioId)
                .map(cardio -> cardioMapper.cardioEditDtoToCardio(cardioEditDto, cardio.getExercise(), cardioId))
                .orElseThrow();
        Cardio savedCardio = cardioRepository.saveAndFlush(cardioToUpdate);
        log.debug("Кардио-упражнение с id '{}' обновлено.", savedCardio.getId());
        return cardioMapper.cardioToCardioReadDto(savedCardio);
    }

    @Override
    public CalisthenicsReadDto findCalisthenicsByExerciseId(Long exerciseId) {
        return calisthenicsRepository.findCalisthenicsByExerciseId(exerciseId)
                .map(calisthenics -> {
                    log.debug("Упражнение-калистеника с id '{}' для exercise с id '{}' найдено.", calisthenics.getId(), exerciseId);
                    return calisthenicsMapper.calisthenicsToCalisthenicsReadDto(calisthenics);
                }).orElseThrow();
    }

    @Override
    public CalisthenicsReadDto createCalisthenics(Long exerciseId, CalisthenicsCreateDto calisthenicsCreateDto) {
        return exerciseRepository.findById(exerciseId)
                .map(exercise -> {
                    if (exercise.getExerciseType().equals(ExerciseType.CALISTHENICS)) {
                        Calisthenics newCalisthenics = calisthenicsMapper.calisthenicsCreateDtoToCalisthenics(calisthenicsCreateDto, exercise);
                        Calisthenics savedCalisthenics = calisthenicsRepository.save(newCalisthenics);
                        log.debug("Упражнение-калистеника с id '{}' для exercise с id '{}' создано.", savedCalisthenics.getId(), exerciseId);
                        return calisthenicsMapper.calisthenicsToCalisthenicsReadDto(savedCalisthenics);
                    }
                    throw new IllegalArgumentException();
                }).orElseThrow();
    }

    @Override
    public CalisthenicsReadDto updateCalisthenics(Long calisthenicsId, CalisthenicsEditDto calisthenicsEditDto) {
        Calisthenics calisthenicsToUpdate = calisthenicsRepository.findById(calisthenicsId)
                .map(calisthenics -> calisthenicsMapper.calisthenicsEditDtoToCalisthenics(calisthenicsEditDto, calisthenics.getExercise(), calisthenicsId))
                .orElseThrow();
        Calisthenics savedCalisthenics = calisthenicsRepository.saveAndFlush(calisthenicsToUpdate);
        log.debug("Упражнение-калистеника с id '{}' обновлено.", calisthenicsId);
        return calisthenicsMapper.calisthenicsToCalisthenicsReadDto(savedCalisthenics);
    }

    @Override
    public StrengthReadDto findStrengthByExerciseId(Long exerciseId) {
        return strengthRepository.findStrengthByExerciseId(exerciseId)
                .map(strength -> {
                    log.debug("Силовое-упражнение с id '{}' для exercise с id '{}' найдено.", strength.getId(), exerciseId);
                    return strengthMapper.strengthToStrengthReadDto(strength);
                }).orElseThrow();
    }

    @Override
    public StrengthReadDto createStrength(Long exerciseId, StrengthCreateDto strengthCreateDto) {
        return exerciseRepository.findById(exerciseId)
                .map(exercise -> {
                    if (exercise.getExerciseType().equals(ExerciseType.STRENGTH)) {
                        Strength newStrength = strengthMapper.strengthCreateDtoToStrength(strengthCreateDto, exercise);
                        Strength savedStrength = strengthRepository.save(newStrength);
                        log.debug("Силовое-упражнение с id '{}' для exercise с id '{}' создано.", savedStrength.getId(), exerciseId);
                        return strengthMapper.strengthToStrengthReadDto(savedStrength);
                    }
                    throw new IllegalArgumentException();
                }).orElseThrow();
    }

    @Override
    public StrengthReadDto updateStrength(Long strengthId, StrengthEditDto strengthEditDto) {
        Strength strengthToUpdate = strengthRepository.findById(strengthId)
                .map(strength -> strengthMapper.strengthEditDtoToStrength(strengthEditDto, strength.getExercise(), strengthId))
                .orElseThrow();
        Strength savedStrength = strengthRepository.saveAndFlush(strengthToUpdate);
        log.debug("Силовое-упражнение с id '{}' для exercise с id '{}' обновлено.", savedStrength.getId(), strengthId);
        return strengthMapper.strengthToStrengthReadDto(savedStrength);
    }
}