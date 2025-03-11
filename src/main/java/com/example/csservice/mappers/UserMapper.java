package com.example.csservice.mappers;

import com.example.csservice.dto.UserDto;
import com.example.csservice.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {


    /**
     * Преобразует объект {@link User} в объект {@link UserDto}.
     * @param user Сущность, которую нужно преобразовать. Может быть null.
     * @return Преобразованный объект {@link UserDto} или null, если входной объект user равен null.
     */
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());

        if (user.getUsername() != null) {
            userDto.setUsername(user.getUsername());
        }
        if (user.getId() != null) {
            userDto.setId(user.getId());
        }

        userDto.setRole(user.getRole());

        return userDto;
    }


    /**
     * Преобразует список объектов {@link User} в список объектов {@link UserDto}.
     *
     * @param users Список сущностей, которые нужно преобразовать. Может содержать null значения.
     * @return Список преобразованных объектов {@link UserDto}, игнорируя null значения.
     */
    public List<UserDto> toUserDtoList(List<User> users) {

        return users.stream()
                .map(this::toUserDto)
                .filter(Objects::nonNull) // Игнорировать null значения
                .collect(Collectors.toList());

    }

    /**
     * Обновляет сущность пользователя на основе {@link UserDto}.
     *
     * @param user   Сущность пользователя, которую нужно обновить.
     * @param userDto DTO, содержащий новые значения для обновления пользователя.
     * @return Обновленная сущность {@link User}.
     * @throws IllegalArgumentException Если user или userDto равны null.
     */
    public User updateUser(User user, UserDto userDto) {
        if (user == null || userDto == null) {
            throw new IllegalArgumentException("User or UserDto cannot be null");
        }

        // Обновляем простые поля
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }
        if (userDto.getId() != null) {
            user.setId(userDto.getId());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }

        return user;
    }


}