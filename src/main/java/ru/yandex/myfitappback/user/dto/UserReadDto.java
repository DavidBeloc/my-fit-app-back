package ru.yandex.myfitappback.user.dto;

import ru.yandex.myfitappback.user.model.Gender;

import java.time.LocalDate;

public record UserReadDto(Long id,
                          String name,
                          Gender gender,
                          LocalDate dateOfBirth,
                          Double weight,
                          Double height,
                          String phoneNumber,
                          String username) {
}
