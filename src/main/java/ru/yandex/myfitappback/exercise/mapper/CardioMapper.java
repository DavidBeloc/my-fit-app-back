package ru.yandex.myfitappback.exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.exercise.dto.CardioCreateDto;
import ru.yandex.myfitappback.exercise.dto.CardioEditDto;
import ru.yandex.myfitappback.exercise.dto.CardioReadDto;
import ru.yandex.myfitappback.exercise.model.Cardio;
import ru.yandex.myfitappback.exercise.model.Exercise;

@Mapper(componentModel = "spring")
public interface CardioMapper {

    CardioReadDto cardioToCardioReadDto(Cardio cardio);

    @Mapping(target = "id", ignore = true)
    Cardio cardioCreateDtoToCardio(CardioCreateDto cardioCreateDto, Exercise exercise);

    @Mapping(target = "id", source = "cardioId")
    Cardio cardioEditDtoToCardio(CardioEditDto cardioEditDto, Exercise exercise, Long cardioId);
}