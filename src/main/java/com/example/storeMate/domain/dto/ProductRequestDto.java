package com.example.storeMate.domain.dto;

import com.example.storeMate.domain.entity.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    private String description;

    @NotNull(message = "가격은 필수입니다.")
    private int price;

    @NotNull(message = "상품 재고는 필수입니다.")
    private int stockQuantity;

    @NotNull(message = "상품 상태는 필수입니다.")
    private ProductStatus status;

    @NotBlank(message = "상품 카테고리는 필수입니다.")
    private String category;

    @NotBlank(message = "상품 브랜드는 필수입니다.")
    private String brand;

    private String thumbnailImageUrl;

    private List<String> imageUrls;

    @Min(value = 0, message = "할인율은 0 이상입니다.")
    private int discountRate;

    private boolean isNew;

    private boolean isBest;

}
