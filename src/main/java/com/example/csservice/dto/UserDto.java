package com.example.csservice.dto;

import com.example.csservice.enams.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Role role;
}
