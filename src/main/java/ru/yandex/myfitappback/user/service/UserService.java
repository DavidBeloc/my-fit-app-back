package ru.yandex.myfitappback.user.service;

import ru.yandex.myfitappback.user.dto.UserCreateDto;
import ru.yandex.myfitappback.user.dto.UserEditDto;
import ru.yandex.myfitappback.user.dto.UserReadDto;

import java.util.Optional;

public interface UserService {

    Optional<UserReadDto> findUserById(Long userId);

    UserReadDto createUser(UserCreateDto userCreateDto);

    UserReadDto updateUser(Long userId, UserEditDto userEditDto);

    boolean deleteUser(Long userId);
}
