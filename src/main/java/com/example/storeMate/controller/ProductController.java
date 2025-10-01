package com.example.storeMate.controller;

import com.example.storeMate.base.security.JwtProvider;
import com.example.storeMate.base.util.rsData.RsData;
import com.example.storeMate.domain.dto.ProductRequestDto;
import com.example.storeMate.domain.dto.ProductResponseDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.domain.entity.Product;
import com.example.storeMate.domain.entity.Role;
import com.example.storeMate.service.MemberService;
import com.example.storeMate.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<RsData<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                                                                    @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtProvider.getUsernameFromToken(token);

        Member member = memberService.findByUsername(username);

        if (member.getRole() == Role.USER) {
            throw new RuntimeException();
        }

        Product product = productService.createProduct(productRequestDto);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStockQuantity(product.getStockQuantity());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setBrand(product.getBrand());
        productResponseDto.setStatus(product.getStatus());
        productResponseDto.setThumbnailImageUrl(product.getThumbnailImageUrl());
        productResponseDto.setImageUrls(product.getImageUrls());
        productResponseDto.setDiscountRate(product.getDiscountRate());
        productResponseDto.setDiscountPrice(product.getDiscountPrice());
        productResponseDto.setNew(product.isNew());
        productResponseDto.setBest(product.isBest());

        return ResponseEntity.ok(new RsData<>("200", "상품이 정상적으로 등록되었습니다.", productResponseDto));

    }
}
