package com.example.csservice.services.impl;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ImageDto;
import com.example.csservice.entity.Client;
import com.example.csservice.entity.Image;
import com.example.csservice.mappers.ClientMapper;
import com.example.csservice.mappers.ImageMapper;
import com.example.csservice.repository.ClientRepository;
import com.example.csservice.repository.ImageRepository;
import com.example.csservice.services.ClientService;
import com.example.csservice.services.ImageService;
import com.example.csservice.utils.FileStorageUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public Image createImage(MultipartFile file) {

        try {
            Image image = FileStorageUtil.createImage(file, uploadDir);
            return imageRepository.save(image);
        }catch (IOException e){
            throw new RuntimeException("Ошибка при создании изображения", e);
        }

    }


    @Override
    public List<ImageDto> getAllImages() {
        return imageRepository.findAll().stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDto getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Изображение с ID " + id + " не найден"));
        return imageMapper.toDto(image);
    }

    @Transactional
    @Override
    public ImageDto updateImage(Long id, MultipartFile file) {

        try {
            deleteImage(id); // Удаление старого изображения
            Image newImage = FileStorageUtil.updateImage(id, file, uploadDir);
            return imageMapper.toDto(imageRepository.save(newImage));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при обновлении изображения", e);
        }
    }

    @Override
    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new EntityNotFoundException("Изображение с ID " + id + " не найдено");
        }
        imageRepository.deleteById(id);
    }

    @Override
    public List<ImageDto> getImageByName(String name) {
        return imageRepository.findImageByAltText(name).stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
    }
}
