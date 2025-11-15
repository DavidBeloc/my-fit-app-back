package ru.yandex.myfitappback.date.dto;

import java.time.LocalDate;

public record DateReadDto(Long id,
                          LocalDate date) {
}
