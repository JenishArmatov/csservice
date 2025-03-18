package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String article; // Артикул

    private Integer count; // Количество на складе

    @ManyToOne(cascade = CascadeType.ALL) // ✅ Каскадное удаление производителя
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // ❌ НЕ ставим REMOVE!
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // 🔹 Одна актуальная цена
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // ✅ Удаляется вместе с Product
    @JoinColumn(name = "current_price_id")
    private Price currentPrice;

    // 🔹 История цен
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Price> priceHistory = new ArrayList<>();

    // 🔹 Изображения товара
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
}
