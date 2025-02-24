package com.example.csservice.repository;

import com.example.csservice.entity.Provision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvisionRepository extends JpaRepository<Provision, Long> {
    List<Provision> getProvisionByName(String name);
}
