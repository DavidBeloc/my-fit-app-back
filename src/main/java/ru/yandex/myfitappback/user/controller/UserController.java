package ru.yandex.myfitappback.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.yandex.myfitappback.user.dto.UserCreateDto;
import ru.yandex.myfitappback.user.dto.UserEditDto;
import ru.yandex.myfitappback.user.dto.UserReadDto;
import ru.yandex.myfitappback.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

/**
    Сделано в рамках теста
*/
    @GetMapping("/test")
    public String test(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            log.info("Роли пользователя: {}", authentication.getAuthorities());
        }
        return "Тест доступа";
    }

    /**
     Сделано в рамках теста
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        log.info("Logout пользователя.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic realm=\"Realm\"") // Триггер для сброса Basic Auth
                .build();
    }

    /**
     Сделано в рамках теста
     */
//    @PostMapping("/login")
//    public ResponseEntity<?> login(Authentication authentication) {
//        log.info("Вход в систему пользователя '{}'.", authentication.getName());
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{userId}")
    public Optional<UserReadDto> findUserById(@PathVariable Long userId) {
        log.info("Поиск пользователя с id '{}'.", userId);
        return userService.findUserById(userId);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        log.info("Регистрация нового пользователя с никнеймом - '{}'.", userCreateDto.name());
        return userService.createUser(userCreateDto);
    }

    @PatchMapping("/{userId}")
    public UserReadDto updateUser(@PathVariable Long userId,
                                  @RequestBody UserEditDto userEditDto) {
        log.info("Обновление данных пользователя с id - '{}'.", userId);
        return userService.updateUser(userId, userEditDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        log.info("Удаление пользователя с id - '{}'.", userId);
        return userService.deleteUser(userId) ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}