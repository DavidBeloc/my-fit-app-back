package ru.yandex.myfitappback.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.exercise.model.Calisthenics;

import java.util.Optional;

public interface CalisthenicsRepository extends JpaRepository<Calisthenics, Long> {

    Optional<Calisthenics> findCalisthenicsByExerciseId(Long exerciseId);
}
