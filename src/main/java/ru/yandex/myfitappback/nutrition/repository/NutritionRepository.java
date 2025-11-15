package ru.yandex.myfitappback.nutrition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.nutrition.model.Nutrition;

import java.util.Optional;

public interface NutritionRepository extends JpaRepository<Nutrition, Long> {

    Optional<Nutrition> findByDateId(Long dateId);
}
