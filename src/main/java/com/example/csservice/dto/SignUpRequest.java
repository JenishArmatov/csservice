package com.example.csservice.dto;

import com.example.csservice.enams.Role;
import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String password;

    private Role roles;
}
