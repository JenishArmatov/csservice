package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "provisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private Double price;
}

