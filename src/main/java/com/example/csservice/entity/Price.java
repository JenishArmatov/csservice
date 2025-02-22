package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String priceType; // Тип цены (например, оптовая, розничная)

    @Column(nullable = false)
    private Double priceValue; // Значение цены
}
