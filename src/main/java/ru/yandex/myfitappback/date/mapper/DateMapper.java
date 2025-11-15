package ru.yandex.myfitappback.date.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.date.dto.DateReadDto;
import ru.yandex.myfitappback.date.dto.DateRequestDto;
import ru.yandex.myfitappback.date.model.Date;
import ru.yandex.myfitappback.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DateMapper {

    DateReadDto dateToDateDto(Date date);

    List<DateReadDto> datesToDtos(List<Date> dates);

    @Mapping(target = "id", ignore = true)
    Date dateRequestToDate(DateRequestDto dateRequestDto, User user);
}
