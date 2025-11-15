package ru.yandex.myfitappback.workout.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.workout.model.Workout;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Page<Workout> findAllByUserId(Long userId, Pageable pageable);

    List<Workout> findAllByDateId(Long dateId);
}
