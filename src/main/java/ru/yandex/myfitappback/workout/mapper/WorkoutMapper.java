package ru.yandex.myfitappback.workout.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.date.model.Date;
import ru.yandex.myfitappback.exercise.model.Exercise;
import ru.yandex.myfitappback.workout.dto.WorkoutEditDto;
import ru.yandex.myfitappback.workout.dto.WorkoutReadDto;
import ru.yandex.myfitappback.workout.dto.WorkoutCreateDto;
import ru.yandex.myfitappback.workout.model.Workout;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    @Mapping(source = "date.date", target = "date")
    WorkoutReadDto workoutToWorkoutDto(Workout workout);

    @Mapping(source = "date.date", target = "date")
    List<WorkoutReadDto> workoutsToWorkoutsDto(List<Workout> workouts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(date)")
    @Mapping(target = "exercises", ignore = true)
    Workout workoutCreateDtoToWorkout(WorkoutCreateDto workoutCreateDto, Date date);

    @Mapping(target = "id", source = "workoutId")
    @Mapping(target = "date", expression = "java(date)")
    Workout workoutEditDtoToWorkout(WorkoutEditDto workoutEditDto, Date date, List<Exercise> exercises, Long workoutId);
}
