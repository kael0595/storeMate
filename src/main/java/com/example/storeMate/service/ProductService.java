package com.example.storeMate.service;

import com.example.storeMate.base.exception.ProductException;
import com.example.storeMate.domain.dto.ProductRequestDto;
import com.example.storeMate.domain.entity.Product;
import com.example.storeMate.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductRequestDto productRequestDto) {

        int discountPrice = productRequestDto.getPrice() * (100 - productRequestDto.getDiscountRate()) / 100;

        Product product = Product.builder()
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .stockQuantity(productRequestDto.getStockQuantity())
                .status(productRequestDto.getStatus())
                .category(productRequestDto.getCategory())
                .brand(productRequestDto.getBrand())
                .thumbnailImageUrl(productRequestDto.getThumbnailImageUrl())
                .imageUrls(productRequestDto.getImageUrls())
                .discountRate(productRequestDto.getDiscountRate())
                .discountPrice(discountPrice)
                .isNew(productRequestDto.isNew())
                .isBest(productRequestDto.isBest())
                .createdAt(LocalDateTime.now())
                .build();

        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductException.NotFound("상품을 찾을 수 없습니다."));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void updateProduct(Product product, ProductRequestDto productRequestDto) {

        int discountPrice = productRequestDto.getPrice() * (100 - productRequestDto.getDiscountRate()) / 100;

        product.setId(product.getId());
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setStatus(productRequestDto.getStatus());
        product.setCategory(productRequestDto.getCategory());
        product.setBrand(productRequestDto.getBrand());
        product.setThumbnailImageUrl(productRequestDto.getThumbnailImageUrl());
        product.setImageUrls(productRequestDto.getImageUrls());
        product.setDiscountRate(productRequestDto.getDiscountRate());
        product.setDiscountPrice(discountPrice);
        product.setNew(productRequestDto.isNew());
        product.setBest(productRequestDto.isBest());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }
}
