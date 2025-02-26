package com.example.csservice.dto;

import com.example.csservice.entity.Product;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Long id;
    private String tagName;
    private Set<Product> products = new HashSet<>();
}

