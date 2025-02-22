package com.example.csservice.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String link;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
