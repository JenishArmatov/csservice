package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imagePath;

    private String altText; // Описание изображения

    private String fileType; // Тип файла (например, png, jpg)

    @ManyToOne
    @JoinColumn(name = "external_id")
    private Product product;
}
