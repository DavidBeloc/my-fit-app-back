package ru.yandex.myfitappback.exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.exercise.dto.CalisthenicsCreateDto;
import ru.yandex.myfitappback.exercise.dto.CalisthenicsEditDto;
import ru.yandex.myfitappback.exercise.dto.CalisthenicsReadDto;
import ru.yandex.myfitappback.exercise.model.Calisthenics;
import ru.yandex.myfitappback.exercise.model.Exercise;

@Mapper(componentModel = "spring")
public interface CalisthenicsMapper {

    CalisthenicsReadDto calisthenicsToCalisthenicsReadDto(Calisthenics calisthenics);

    @Mapping(target = "id", ignore = true)
    Calisthenics calisthenicsCreateDtoToCalisthenics(CalisthenicsCreateDto calisthenicsCreateDto, Exercise exercise);

    @Mapping(target = "id", source = "calisthenicsId")
    Calisthenics calisthenicsEditDtoToCalisthenics(CalisthenicsEditDto calisthenicsEditDto, Exercise exercise, Long calisthenicsId);
}