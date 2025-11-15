package ru.yandex.myfitappback.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.exercise.model.Exercise;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByWorkoutId(Long workoutId);
}
