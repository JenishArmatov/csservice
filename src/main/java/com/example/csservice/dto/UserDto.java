package com.example.csservice.dto;

import com.example.csservice.enams.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Role role;
}
