package ru.yandex.myfitappback.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.exercise.model.Cardio;

import java.util.Optional;

public interface CardioRepository extends JpaRepository<Cardio, Long> {

    Optional<Cardio> findCardioByExerciseId(Long exerciseId);
}
