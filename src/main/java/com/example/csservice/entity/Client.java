package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameClient;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}
