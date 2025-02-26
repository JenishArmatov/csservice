package com.example.csservice.mappers;

import com.example.csservice.dto.ImageDto;
import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.*;
import com.example.csservice.services.ManufacturerService;
import com.example.csservice.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ManufacturerService manufacturerService;
    private final ManufacturerMapper manufacturerMapper;
    private final TagService tagService;

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .article(product.getArticle())
                .count(product.getCount())
                .manufacturerId(product.getManufacturer().getId())
                .manufacturerName(product.getManufacturer().getManufactureText())
                .tags(product.getTags().stream().map(Tag::getTagName).collect(Collectors.toSet()))
                .prices(product.getPriceHistory().stream().map(this::mapPriceToDto).collect(Collectors.toList()))
                .images(product.getImages().stream().map(this::mapImageToDto).collect(Collectors.toList()))
                .build();
    }

    public Product toEntity(ProductDto dto, List<Image> images) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setArticle(dto.getArticle());
        product.setCount(dto.getCount());

        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerService.getManufacturerById(dto.getManufacturerId()));
        product.setManufacturer(manufacturer);

        // Получаем список тегов через `TagService`
        Set<Tag> tags = dto.getTags().stream()
                .flatMap(tagName -> tagService.getTagsByTagName(tagName).stream()
                        .map(this::mapTagDtoToEntity))
                .collect(Collectors.toSet());
        product.setTags(tags);

        product.setImages(images);

        return product;
    }

    public void updateEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setArticle(dto.getArticle());
        product.setCount(dto.getCount());

        // Обновление производителя, если он изменился
        if (!product.getManufacturer().getId().equals(dto.getManufacturerId())) {
            Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerService.getManufacturerById(dto.getManufacturerId()));
            product.setManufacturer(manufacturer);
        }

        // Обновление тегов
        Set<Tag> updatedTags = dto.getTags().stream()
                .flatMap(tagName -> tagService.getTagsByTagName(tagName).stream()
                        .map(this::mapTagDtoToEntity))
                .collect(Collectors.toSet());
        product.setTags(updatedTags);
    }

    private PriceDto mapPriceToDto(Price price) {
        return PriceDto.builder()
                .id(price.getId())
                .productId(price.getId())
                .priceType(price.getPriceType())
                .priceValue(price.getPriceValue())
                .build();
    }

    private ImageDto mapImageToDto(Image image) {
        return new ImageDto(image.getId(), image.getImagePath(), image.getAltText(), image.getFileType());
    }

    private Tag mapTagDtoToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setTagName(tagDto.getTagName());
        return tag;
    }


}
