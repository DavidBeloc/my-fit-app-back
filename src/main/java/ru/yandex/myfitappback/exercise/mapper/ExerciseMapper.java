package ru.yandex.myfitappback.exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.exercise.dto.ExerciseCreateDto;
import ru.yandex.myfitappback.exercise.dto.ExerciseEditDto;
import ru.yandex.myfitappback.exercise.dto.ExerciseReadDto;
import ru.yandex.myfitappback.exercise.model.Exercise;
import ru.yandex.myfitappback.workout.model.Workout;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    List<ExerciseReadDto> exercisesToExercisesReadDto(List<Exercise> exercises);

    ExerciseReadDto exerciseToExerciseReadDto(Exercise exercise);

    @Mapping(target = "id", ignore = true)
    Exercise exerciseCreateDtoToExercise(Workout workout, ExerciseCreateDto exerciseCreateDto);

    @Mapping(target = "id", source = "exerciseId")
    Exercise exerciseEditDtoToExercise(ExerciseEditDto exerciseEditDto, Workout workout, Long exerciseId);
}