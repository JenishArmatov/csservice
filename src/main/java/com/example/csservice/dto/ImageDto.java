package com.example.csservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    private Long id;
    private String imagePath; // Путь к изображению
    private String altText; // Альтернативный текст
    private String fileType; // Тип файла
}

