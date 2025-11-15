package ru.yandex.myfitappback.date.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.myfitappback.date.model.Date;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DateRepository extends JpaRepository<Date, Long> {

    Optional<Date> findByUserIdAndDate(Long userId, LocalDate date);

    @Query(value = "SELECT d FROM Date d WHERE d.user.id = ?1 AND EXTRACT(YEAR FROM d.date) = ?2 AND EXTRACT(MONTH FROM d.date) = ?3")
    List<Date> findDatesByYearAndMonth(Long userId, Integer year, Integer month);

    @Query(value = "SELECT d FROM Date d WHERE d.user.id = ?1 AND EXTRACT(YEAR FROM d.date) = ?2")
    List<Date> findDatesByYear(Long userId, Integer year);
}