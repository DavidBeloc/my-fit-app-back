package ru.yandex.myfitappback.date.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.myfitappback.date.dto.DateReadDto;
import ru.yandex.myfitappback.date.dto.DateRequestDto;
import ru.yandex.myfitappback.date.service.DateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/dates")
@RequiredArgsConstructor
@Slf4j
public class DateController {

    private final DateService dateService;

    @GetMapping("/date")
    public DateReadDto findDate(@PathVariable Long userId,
                                @RequestBody DateRequestDto dateRequestDto) {
        log.info("Поиск даты для пользователя с id '{}'.", userId);
        return dateService.findDate(userId, dateRequestDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<DateReadDto> findDatesByYearAndMonth(@PathVariable Long userId,
                                                     @RequestParam Integer year,
                                                     @RequestParam(required = false) Integer month) {
        log.info("Поиск дат для пользователя с id '{}' за год '{}' и месяц '{}'.", userId, year, month);
        List<DateReadDto> dates = dateService.findDatesByYearAndMonth(userId, year, month);
        if (dates.isEmpty()) {
            log.info("Даты для пользователя с id '{}' за год '{}' и месяц '{}' не найдены.", userId, year, month);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return dates;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DateReadDto createDate(@PathVariable Long userId,
                                  @RequestBody DateRequestDto dateRequestDto) {
        log.info("Создание даты для пользователя с id '{}'.", userId);
        return dateService.createDate(userId, dateRequestDto);
    }

    @PatchMapping("/{dateId}")
    public DateReadDto updateDate(@PathVariable Long dateId,
                                  @RequestBody DateRequestDto dateRequestDto) {
        log.info("Обновление даты с id '{}'.", dateId);
        return dateService.updateDate(dateId, dateRequestDto);
    }

    @DeleteMapping("/{dateId}")
    public ResponseEntity<?> deleteDate(@PathVariable Long dateId) {
        log.info("Удаление даты с id '{}'.", dateId);
        return dateService.deleteDate(dateId) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
