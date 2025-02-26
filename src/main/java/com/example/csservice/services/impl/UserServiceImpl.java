package com.example.csservice.services.impl;

import com.example.csservice.dto.Response;
import com.example.csservice.dto.UserDto;
import com.example.csservice.enams.Role;
import com.example.csservice.entity.User;
import com.example.csservice.mappers.UserMapper;
import com.example.csservice.repository.UserRepository;
import com.example.csservice.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с пользователями.
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения пользователей,
 * а также управления их ролями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("A user with this name already exists");
        }
        log.info("Create user with username: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDto getUserResponseById(Long id) {
        User user = getById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserDto userDto = userMapper.toUserDto(user);
        return userDto;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    @Override
    public Response<List<UserDto>> getAllUsersWithPagination(int firstPage, int pageSize, String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(firstPage, pageSize, Sort.by(direction, sort[0]));
        Page<User> pages = userRepository.findAll(pageable);
        List<User> users = pages.getContent();
        List<UserDto> usersDto = userMapper.toUserDtoList(users);
        return new Response<>("All users retrieved successfully", usersDto);
    }


    @Override
    public Response<UserDto> setRole(String roleName, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = Role.valueOf(roleName);
        user.setRole(role);
        userRepository.save(user);
        return new Response<>("User", userMapper.toUserDto(user));
    }


    @Override
    public Response<UserDto> updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User newUser = userMapper.updateUser(user, userDto);
        userRepository.save(newUser);
        UserDto newUserDto = userMapper.toUserDto(newUser);
        return new Response<>("User updated successfully", newUserDto);
    }


    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}