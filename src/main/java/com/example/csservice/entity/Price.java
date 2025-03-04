package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String priceType; // Тип цены (например, "Оптовая", "Розничная")

    @Column(nullable = false)
    private Double priceValue; // Значение цены

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean current; // Флаг: актуальная цена

    @Column(nullable = false)
    private LocalDateTime validFrom; // Дата начала действия цены

    @Column
    private LocalDateTime validTo; // Дата окончания действия (null, если активна)

}
