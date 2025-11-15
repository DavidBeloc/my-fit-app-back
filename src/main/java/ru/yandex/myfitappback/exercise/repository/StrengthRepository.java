package ru.yandex.myfitappback.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.exercise.model.Strength;

import java.util.Optional;

public interface StrengthRepository extends JpaRepository<Strength, Long> {

    Optional<Strength> findStrengthByExerciseId(Long exerciseId);
}
