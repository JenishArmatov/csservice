package com.example.csservice.services;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ImageDto;
import com.example.csservice.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image createImage(MultipartFile file);
    List<ImageDto> getAllImages();
    ImageDto getImageById(Long id);
    ImageDto updateImage(Long id, MultipartFile file);
    void deleteImage(Long id);
    List<ImageDto> getImageByName(String name);
}