package com.example.storeMate.domain.dto;

import com.example.storeMate.domain.entity.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private int price;

    private int stockQuantity;

    private ProductStatus status;

    private String category;

    private String brand;

    private String thumbnailImageUrl;

    private List<String> imageUrls;

    private int discountRate;

    private int discountPrice;

    @JsonProperty("isNew")
    private boolean isNew;

    @JsonProperty("isBest")
    private boolean isBest;

    private boolean deleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
