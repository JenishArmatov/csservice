package com.example.csservice.services;

import com.example.csservice.dto.Response;
import com.example.csservice.dto.UserDto;
import com.example.csservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create (User user);
    User getCurrentUser();
    Optional<User> getById(Long id);
    User getByUsername(String username);
    UserDto getUserResponseById(Long id);
    Response<List<UserDto>> getAllUsersWithPagination(int firstPage, int pageSize, String[] sort);
    Response<UserDto> setRole(String role, Long id);
    Response<UserDto> updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
