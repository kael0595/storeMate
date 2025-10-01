package com.example.storeMate.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int price;

    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String category;

    private String brand;

    private String thumbnailImageUrl;

    private List<String> imageUrls;

    private int discountRate;

    private int discountPrice;

    private boolean isNew;

    private boolean isBest;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
