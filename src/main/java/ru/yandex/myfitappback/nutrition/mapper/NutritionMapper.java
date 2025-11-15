package ru.yandex.myfitappback.nutrition.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.date.model.Date;
import ru.yandex.myfitappback.nutrition.dto.NutritionCreateDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionEditDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionReadDto;
import ru.yandex.myfitappback.nutrition.model.Nutrition;

@Mapper(componentModel = "spring")
public interface NutritionMapper {

    @Mapping(source = "date.date", target = "date")
    NutritionReadDto nutritionToNutritionDto(Nutrition nutrition);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(date)")
    Nutrition nutritionCreateDtoToNutrition(NutritionCreateDto nutritionCreateDto, Date date);


    /* Возможно нужно заигнорить date.id, т.к. переприсвоит, но это не точно потому что по очереди nutritionId идёт последним
       и так и так переприсвоит, можно в тестах проверить */

    @Mapping(target = "id", source = "nutritionId")
    @Mapping(target = "date", expression = "java(date)")
    Nutrition nutritionEditDtoToNutrition(NutritionEditDto nutritionEditDto, Date date, Long nutritionId);
}
