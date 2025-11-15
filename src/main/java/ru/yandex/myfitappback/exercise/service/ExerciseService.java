package ru.yandex.myfitappback.exercise.service;

import ru.yandex.myfitappback.exercise.dto.*;

import java.util.List;

public interface ExerciseService {

    List<ExerciseReadDto> findExercisesByWorkoutId(Long workoutId);

    ExerciseReadDto findExerciseById(Long exerciseId);

    ExerciseReadDto createExercise(Long workoutId, ExerciseCreateDto exerciseCreateDto);

    ExerciseReadDto updateExercise(Long exerciseId, ExerciseEditDto exerciseEditDto);

    boolean deleteExercise(Long exerciseId);

    CardioReadDto findCardioByExerciseId(Long exerciseId);

    CardioReadDto createCardio(Long exerciseId, CardioCreateDto cardioCreateDto);

    CardioReadDto updateCardio(Long cardioId, CardioEditDto cardioEditDto);

    CalisthenicsReadDto findCalisthenicsByExerciseId(Long exerciseId);

    CalisthenicsReadDto createCalisthenics(Long exerciseId, CalisthenicsCreateDto calisthenicsCreateDto);

    CalisthenicsReadDto updateCalisthenics(Long calisthenicsId, CalisthenicsEditDto calisthenicsEditDto);

    StrengthReadDto findStrengthByExerciseId(Long exerciseId);

    StrengthReadDto createStrength(Long exerciseId, StrengthCreateDto strengthCreateDto);

    StrengthReadDto updateStrength(Long strengthId, StrengthEditDto strengthEditDto);
}
