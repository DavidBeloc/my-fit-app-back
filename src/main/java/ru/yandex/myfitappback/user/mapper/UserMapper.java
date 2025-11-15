package ru.yandex.myfitappback.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.myfitappback.user.dto.UserCreateDto;
import ru.yandex.myfitappback.user.dto.UserEditDto;
import ru.yandex.myfitappback.user.dto.UserReadDto;
import ru.yandex.myfitappback.user.model.User;
import ru.yandex.myfitappback.user.model.UserRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReadDto userToUserReadDto(User user);

    List<UserReadDto> usersToUsersDto(List<User> users);

    @Mapping(target = "id", ignore = true)
    User userCreateDtoToUser(UserCreateDto userCreateDto);

    @Mapping(target = "id", source = "userId")
    User userEditDtoToUser(Long userId, UserEditDto userEditDto);
}
