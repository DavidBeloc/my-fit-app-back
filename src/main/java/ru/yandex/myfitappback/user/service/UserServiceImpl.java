package ru.yandex.myfitappback.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.myfitappback.user.dto.UserCreateDto;
import ru.yandex.myfitappback.user.dto.UserEditDto;
import ru.yandex.myfitappback.user.dto.UserReadDto;
import ru.yandex.myfitappback.user.mapper.UserMapper;
import ru.yandex.myfitappback.user.model.User;
import ru.yandex.myfitappback.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserReadDto> findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        log.debug("Пользователь с id '{}' найден.", userId);
        return user.map(userMapper::userToUserReadDto);
    }

    @Override
    public UserReadDto createUser(UserCreateDto userCreateDto) {
        User newUser = userMapper.userCreateDtoToUser(userCreateDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(newUser);
        log.debug("Пользователь username '{}' успешно создан.", userCreateDto.username());
        return userMapper.userToUserReadDto(savedUser);
    }

    @Override
    public UserReadDto updateUser(Long userId, UserEditDto userEditDto) {
        User userToUpdate = userMapper.userEditDtoToUser(userId, userEditDto);
        User savedUser = userRepository.saveAndFlush(userToUpdate);
        log.debug("Пользователь с id '{}' успешно обновлен.", userId);
        return userMapper.userToUserReadDto(savedUser);
    }

    @Override
    public boolean deleteUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    log.debug("Пользователь с id '{}' успешно удален.", userId);
                    return true;
                }).orElse(false);
    }
}
