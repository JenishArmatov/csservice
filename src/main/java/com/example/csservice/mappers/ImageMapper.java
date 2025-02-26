package com.example.csservice.mappers;

import com.example.csservice.dto.ClientDto;
import com.example.csservice.dto.ImageDto;
import com.example.csservice.entity.Client;
import com.example.csservice.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageDto toDto(Image image) {
        if (image == null) {
            return null;
        }

        return ImageDto.builder()
                .id(image.getId())
                .imagePath(image.getImagePath())
                .altText(image.getAltText())
                .fileType(image.getFileType())
                .build();
    }

    public Image toEntity(ImageDto imageDto) {
        if (imageDto == null) {
            return null;
        }

        Image image = new Image();
        image.setId(imageDto.getId());
        image.setImagePath(imageDto.getImagePath());
        image.setAltText(imageDto.getAltText());
        image.setFileType(imageDto.getFileType());

        return image;
    }
}
