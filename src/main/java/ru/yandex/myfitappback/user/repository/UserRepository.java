package ru.yandex.myfitappback.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.myfitappback.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
