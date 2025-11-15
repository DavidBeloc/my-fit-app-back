package ru.yandex.myfitappback.date.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.myfitappback.date.dto.DateReadDto;
import ru.yandex.myfitappback.date.dto.DateRequestDto;
import ru.yandex.myfitappback.date.mapper.DateMapper;
import ru.yandex.myfitappback.date.model.Date;
import ru.yandex.myfitappback.date.repository.DateRepository;
import ru.yandex.myfitappback.user.model.User;
import ru.yandex.myfitappback.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DateServiceImpl implements DateService {

    private final DateRepository dateRepository;

    private final UserRepository userRepository;

    private final DateMapper dateMapper;

    @Override
    public Optional<DateReadDto> findDate(Long userId, DateRequestDto dateRequestDto) {
        Optional<Date> date = dateRepository.findByUserIdAndDate(userId, dateRequestDto.date());
        log.debug("Дата '{}' для пользователя с id '{}' найдена.", dateRequestDto.date(), userId);
        return date.map(dateMapper::dateToDateDto);
    }

    @Override
    public List<DateReadDto> findDatesByYearAndMonth(Long userId, Integer year, Integer month) {
        if (month != null) {
            List<Date> dates = dateRepository.findDatesByYearAndMonth(userId, year, month);
            log.debug("Даты за '{}' год и '{}' месяц  для пользователя с id '{}' найдены.", year, month, userId);
            return dateMapper.datesToDtos(dates);
        }
        List<Date> dates = dateRepository.findDatesByYear(userId, year);
        log.debug("Даты за '{}' год для пользователя с id '{}' найдены.", year, userId);
        return dateMapper.datesToDtos(dates);
    }

    @Override
    public DateReadDto createDate(Long userId, DateRequestDto dateRequestDto) {
        if (dateRepository.findByUserIdAndDate(userId, dateRequestDto.date()).isEmpty()) {
            User user = getUser(userId);
            Date newDate = dateMapper.dateRequestToDate(dateRequestDto, user);
            Date savedDate = dateRepository.save(newDate);
            log.debug("Дата '{}' для пользователя с id '{}' создана.", dateRequestDto.date(), userId);
            return dateMapper.dateToDateDto(savedDate);
        } else {
            log.debug("Дата '{}' для пользователя с id '{}' уже существует.", dateRequestDto.date(), userId);
            throw new EntityExistsException();
        }
    }

    @Override
    public DateReadDto updateDate(Long dateId, DateRequestDto dateRequestDto) {
        Date dateToUpdate = dateRepository.findById(dateId)
                .map(date -> {
                    if (date.getDate().isEqual(dateRequestDto.date())) {
                        log.debug("Дата '{}' для пользователя с id '{}' уже существует.", dateRequestDto.date(), date.getUser().getId());
                        throw new EntityExistsException();
                    }
                    date.setDate(dateRequestDto.date());
                    return date;
                }).orElseThrow();
        Date savedDate = dateRepository.saveAndFlush(dateToUpdate);
        log.debug("Дата '{}' для пользователя с id '{}' обновлена.", dateRequestDto.date(), dateToUpdate.getUser().getId());
        return dateMapper.dateToDateDto(savedDate);
    }

    @Override
    public boolean deleteDate(Long dateId) {
        return dateRepository.findById(dateId)
                .map(date -> {
                    dateRepository.delete(date);
                    dateRepository.flush();
                    log.debug("Дата '{}' для пользователя с id '{}' удалена.", date.getDate(), date.getUser().getId());
                    return true;
                }).orElse(false);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
