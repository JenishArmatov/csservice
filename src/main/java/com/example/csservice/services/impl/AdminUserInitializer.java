package com.example.csservice.services.impl;

import com.example.csservice.enams.Role;
import com.example.csservice.entity.User;
import com.example.csservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        Role adminRole = Role.ROLE_ADMIN;
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(adminRole)
                    .build();
            userRepository.save(admin);
            System.out.println("=== Администратор СОЗДАН ===");
        } else {
            System.out.println("=== Администратор УЖЕ СУЩЕСТВУЕТ ===");
        }
    }


}