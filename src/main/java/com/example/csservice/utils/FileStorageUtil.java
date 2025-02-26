package com.example.csservice.utils;

import com.example.csservice.entity.Image;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileStorageUtil {

    public static Image createImage(MultipartFile file, String uploadDir) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Файл не должен быть пустым");
        }

        // Создаем папку, если ее нет
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Генерируем уникальное имя файла
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Полный путь к файлу
        Path filePath = uploadPath.resolve(uniqueFilename);

        // Сохраняем файл
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Создаем сущность Image
        Image image = new Image();
        image.setImagePath(filePath.toString()); // Путь к файлу
        image.setAltText(file.getName());
        image.setFileType(file.getContentType());

        return image;
    }
}
