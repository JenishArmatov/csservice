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
    private final TagMapper tagMapper;
    private final PriceMapper priceMapper;

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setArticle(product.getArticle());
        productDto.setCount(product.getCount());
        productDto.setManufacturerId(product.getManufacturer().getId());
        productDto.setManufacturerName(product.getManufacturer().getManufactureText());
        productDto.setTags(product.getTags().stream().map(Tag::getTagName).collect(Collectors.toSet()));
        productDto.setPrices(product.getPriceHistory().stream().map(this::mapPriceToDto).collect(Collectors.toList()));
        productDto.setImages(product.getImages().stream().map(this::mapImageToDto).collect(Collectors.toList()));
        return productDto;
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
                        .map(tagMapper::toEntity))
                .collect(Collectors.toSet());
        product.setTags(tags);
        for (Image image : images) {
            image.setProduct(product);
        }

        product.setImages(images);

        return product;
    }

    public void updateEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setArticle(dto.getArticle());
        product.setCount(dto.getCount());

//        // Обновление производителя, если он изменился
//        if (!product.getManufacturer().getId().equals(dto.getManufacturerId())) {
//            Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerService.getManufacturerById(dto.getManufacturerId()));
//            product.setManufacturer(manufacturer);
//        }
//
//        // Обновление тегов
//        Set<Tag> updatedTags = dto.getTags().stream()
//                .flatMap(tagName -> tagService.getTagsByTagName(tagName).stream()
//                        .map(this::mapTagDtoToEntity))
//                .collect(Collectors.toSet());
//        product.setTags(updatedTags);
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
