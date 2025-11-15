package ru.yandex.myfitappback.nutrition.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.myfitappback.date.repository.DateRepository;
import ru.yandex.myfitappback.nutrition.dto.NutritionCreateDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionEditDto;
import ru.yandex.myfitappback.nutrition.dto.NutritionReadDto;
import ru.yandex.myfitappback.nutrition.mapper.NutritionMapper;
import ru.yandex.myfitappback.nutrition.model.Nutrition;
import ru.yandex.myfitappback.nutrition.repository.NutritionRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NutritionServiceImpl implements NutritionService {

    private final NutritionRepository nutritionRepository;

    private final DateRepository dateRepository;

    private final NutritionMapper nutritionMapper;

    @Override
    public Optional<NutritionReadDto> findNutritionByDate(Long userId, LocalDate date) {
        return dateRepository.findByUserIdAndDate(userId, date)
                .map(foundDate -> {
                    Optional<Nutrition> nutrition = nutritionRepository.findByDateId(foundDate.getId());
                    log.debug("Питание за дату '{}' для пользователя с id '{}' найдено.", foundDate.getDate(), userId);
                    return nutrition.map(nutritionMapper::nutritionToNutritionDto);
                }).orElseThrow();
    }

    @Override
    public NutritionReadDto createNutrition(Long userId, NutritionCreateDto nutritionCreateDto) {
        return dateRepository.findByUserIdAndDate(userId, nutritionCreateDto.date())
                .map(foundDate -> {
                    if (nutritionRepository.findByDateId(foundDate.getId()).isEmpty()) {
                        Nutrition newNutrition = nutritionMapper.nutritionCreateDtoToNutrition(nutritionCreateDto, foundDate);
                        Nutrition savedNutrition = nutritionRepository.save(newNutrition);
                        log.debug("Питание за дату '{}' для пользователя с id '{}' создано.", foundDate.getDate(), userId);
                        return nutritionMapper.nutritionToNutritionDto(savedNutrition);
                    } else throw new EntityExistsException();
                }).orElseThrow();
    }

    @Override
    public NutritionReadDto updateNutrition(Long nutritionId, NutritionEditDto nutritionEditDto) {
        Nutrition nutritionToUpdate = nutritionRepository.findById(nutritionId)
                .map(nutrition -> {
                    Long userId = nutrition.getDate().getUser().getId();
                    return dateRepository.findByUserIdAndDate(userId, nutritionEditDto.date())
                            .map(date -> nutritionMapper.nutritionEditDtoToNutrition(nutritionEditDto, date, nutritionId))
                            .orElseThrow();
                }).orElseThrow();
        Nutrition savedNutrition = nutritionRepository.saveAndFlush(nutritionToUpdate);
        log.debug("Питание за дату '{}' для пользователя с id '{}' обновлено.", savedNutrition.getDate().getDate(), savedNutrition.getDate().getUser().getId());
        return nutritionMapper.nutritionToNutritionDto(savedNutrition);
    }

    @Override
    public boolean deleteNutrition(Long nutritionId) {
        return nutritionRepository.findById(nutritionId)
                .map(nutrition -> {
                    nutritionRepository.delete(nutrition);
                    nutritionRepository.flush();
                    log.debug("Питание за дату '{}' для пользователя с id '{}' удалено.", nutrition.getDate().getDate(), nutrition.getDate().getUser().getId());
                    return true;
                }).orElse(false);
    }
}
