package ru.yandex.myfitappback.exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.exercise.dto.StrengthCreateDto;
import ru.yandex.myfitappback.exercise.dto.StrengthEditDto;
import ru.yandex.myfitappback.exercise.dto.StrengthReadDto;
import ru.yandex.myfitappback.exercise.model.Exercise;
import ru.yandex.myfitappback.exercise.model.Strength;

@Mapper(componentModel = "spring")
public interface StrengthMapper {

    StrengthReadDto strengthToStrengthReadDto(Strength strength);

    @Mapping(target = "id", ignore = true)
    Strength strengthCreateDtoToStrength(StrengthCreateDto strengthCreateDto, Exercise exercise);

    @Mapping(target = "id", source = "strengthId")
    Strength strengthEditDtoToStrength(StrengthEditDto strengthEditDto, Exercise exercise, Long strengthId);
}
