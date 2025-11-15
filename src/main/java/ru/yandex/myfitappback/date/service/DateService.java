package ru.yandex.myfitappback.date.service;

import ru.yandex.myfitappback.date.dto.DateReadDto;
import ru.yandex.myfitappback.date.dto.DateRequestDto;

import java.util.List;
import java.util.Optional;

public interface DateService {

    Optional<DateReadDto> findDate(Long userId, DateRequestDto dateRequestDto);

    DateReadDto createDate(Long userId, DateRequestDto dateRequestDto);

    DateReadDto updateDate(Long dateId, DateRequestDto dateRequestDto);

    boolean deleteDate(Long dateId);    
    
    List<DateReadDto> findDatesByYearAndMonth(Long userId, Integer year, Integer month);
}
