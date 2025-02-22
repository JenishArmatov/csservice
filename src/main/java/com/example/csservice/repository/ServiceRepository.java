package com.example.csservice.repository;

import com.example.csservice.entity.Provision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Provision, Long> {
}
