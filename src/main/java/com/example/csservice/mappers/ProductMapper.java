package com.example.csservice.mappers;

import com.example.csservice.dto.ImageDto;
import com.example.csservice.dto.PriceDto;
import com.example.csservice.dto.ProductDto;
import com.example.csservice.dto.TagDto;
import com.example.csservice.entity.*;
import com.example.csservice.repository.*;
import com.example.csservice.services.ManufacturerService;
import com.example.csservice.services.PriceService;
import com.example.csservice.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ManufacturerService manufacturerService;
    private final ManufacturerMapper manufacturerMapper;
    private final ManufacturerRepository manufacturerRepository;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final PriceMapper priceMapper;
    private final PriceRepository priceRepository;
    private final PriceService priceService;
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;


    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setArticle(product.getArticle());
        productDto.setCount(product.getCount());
        productDto.setManufacturerId(product.getManufacturer().getId());
        productDto.setManufacturerName(product.getManufacturer().getManufactureText());
        productDto.setPrice(priceMapper.toDto(product.getCurrentPrice()));
        productDto.setTags(product.getTags().stream().map(tagMapper::toDto).collect(Collectors.toSet()));
        productDto.setPrices(product.getPriceHistory().stream().map(this::mapPriceToDto).collect(Collectors.toList()));
        productDto.setImages(product.getImages().stream().map(this::mapImageToDto).collect(Collectors.toList()));
        return productDto;
    }

    @Transactional
    public Product toEntity(ProductDto productDto, List<Image> images) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setArticle(productDto.getArticle());
        product.setCount(productDto.getCount());
        for (Image image : images) {
            image.setProduct(product);
            imageRepository.save(image);
        }
        product.setImages(images);
        Manufacturer manufacturer = Manufacturer.builder()
                .manufactureText(productDto.getManufacturerName())
                .build();

        manufacturerRepository.save(manufacturer);

        product.setManufacturer(manufacturer);

        Product newProduct = productRepository.save(product);

        PriceDto priceDto = productDto.getPrice();
        priceDto.setProductId(newProduct.getId());

        PriceDto newPriceDto = priceService.createPrice(priceDto);

        newProduct.setCurrentPrice(priceMapper.toEntity(newPriceDto, newProduct));



        List<Price> prices = new ArrayList<>();
        for (PriceDto dto : productDto.getPrices()){
            Price price = priceMapper.toEntity(dto, newProduct);
            prices.add(price);
        }


        newProduct.getPriceHistory().clear(); // Очистить старые цены
        newProduct.getPriceHistory().addAll(prices); // Добавить новые


        Set<Tag> tags = productDto.getTags().stream()
                .map(tagDto -> tagRepository.findByTagName(tagDto.getTagName())
                        .orElseGet(() -> tagRepository.save(tagMapper.toEntity(tagDto))))
                .collect(Collectors.toSet());
        newProduct.setTags(tags);


        return newProduct;
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
                .productId(price.getProduct().getId())
                .current(price.isCurrent())
                .priceType(price.getPriceType())
                .priceValue(price.getPriceValue())
                .currency(price.getCurrency())
                .validFrom(price.getValidFrom())
                .validTo(price.getValidTo())
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
