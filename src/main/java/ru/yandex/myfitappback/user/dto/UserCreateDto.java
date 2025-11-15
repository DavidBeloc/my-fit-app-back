package ru.yandex.myfitappback.user.dto;

import jakarta.validation.constraints.*;
import ru.yandex.myfitappback.user.model.Gender;
import ru.yandex.myfitappback.user.model.UserRole;

import java.time.LocalDate;

public record UserCreateDto(@NotBlank(message = "Имя не может быть пустым.")
                            String name,
                            @NotNull(message = "Пол не может быть пустым.")
                            Gender gender,
                            @Past(message = "Дата рождения должна быть указана в прошедшем времени.")
                            LocalDate dateOfBirth,
                            @NotNull(message = "Значение веса должно быть указано и иметь положительное число.")
                            @Positive(message = "Значение веса должно быть указано и иметь положительное число.")
                            Double weight,
                            @NotNull(message = "Значение роста должно быть указано и иметь положительное число.")
                            @Positive(message = "Значение роста должно быть указано и иметь положительное число.")
                            Double height,
                            @Size(min = 6, max = 128, message = "Пароль должен быть от 6 до 128 символов.")
                            String password,
                            @NotNull(message = "Роль не может быть пустой.")
                            UserRole role,
                            @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Номер телефона должен содержать 10-15 цифр.")
                            String phoneNumber,
                            @Email(message = "Некорректный email.")
                            String username) {
}
