package com.example.csservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity; // Количество данного товара в корзине
}
