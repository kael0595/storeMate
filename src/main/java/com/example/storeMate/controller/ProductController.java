package com.example.storeMate.controller;

import com.example.storeMate.auth.service.AuthService;
import com.example.storeMate.base.exception.ProductException;
import com.example.storeMate.base.util.rsData.RsData;
import com.example.storeMate.domain.dto.PageResponseDto;
import com.example.storeMate.domain.dto.ProductRequestDto;
import com.example.storeMate.domain.dto.ProductResponseDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.domain.entity.Product;
import com.example.storeMate.domain.entity.Role;
import com.example.storeMate.service.MemberService;
import com.example.storeMate.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final MemberService memberService;

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<RsData<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                                                                    @RequestHeader("Authorization") String authorizeHeader) {

        Member member = authService.getMemberFromAuthorizationHeader(authorizeHeader);

        if (member.getRole() == Role.USER) {
            throw new ProductException.Forbidden("상품 등록 권한이 없습니다.");
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

    @GetMapping("/{id}")
    public ResponseEntity<RsData<ProductResponseDto>> detail(@PathVariable("id") Long id) {

        Product product = productService.findById(id);

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

        return ResponseEntity.ok(new RsData<>("200", "상품 조회에 성공하였습니다.", productResponseDto));
    }

    @GetMapping
    public ResponseEntity<RsData<PageResponseDto>> productList(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                               @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                                                               @RequestParam(value = "kw", defaultValue = "") String kw) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Product> products = productService.findByKw(kw, pageable);

        List<ProductResponseDto> content = products.getContent().stream()
                .map(
                        product -> {
                            ProductResponseDto productResponseDto = new ProductResponseDto();
                            productResponseDto.setId(product.getId());
                            productResponseDto.setName(product.getName());
                            productResponseDto.setPrice(product.getPrice());
                            productResponseDto.setStockQuantity(product.getStockQuantity());
                            productResponseDto.setStatus(product.getStatus());
                            productResponseDto.setThumbnailImageUrl(product.getThumbnailImageUrl());
                            productResponseDto.setImageUrls(product.getImageUrls());
                            productResponseDto.setDescription(product.getDescription());
                            productResponseDto.setBrand(product.getBrand());
                            productResponseDto.setCategory(product.getCategory());
                            productResponseDto.setDiscountPrice(product.getDiscountPrice());
                            productResponseDto.setDiscountRate(product.getDiscountRate());
                            productResponseDto.setNew(product.isNew());
                            productResponseDto.setBest(product.isBest());
                            return productResponseDto;
                        })
                .toList();

        PageResponseDto<ProductResponseDto> pageResponseDto = new PageResponseDto<>(
                content,
                products.getSize(),
                products.getNumber() + 1,
                products.getTotalElements(),
                products.getTotalPages()
        );

        return ResponseEntity.ok(new RsData<>("200", "상품 목록이 정상적으로 출력되었습니다", pageResponseDto));
    }

    @PatchMapping("{id}")
    public ResponseEntity<RsData<ProductResponseDto>> updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                                                                    @RequestHeader("Authorization") String authorizeHeader,
                                                                    @PathVariable("id") Long id) {

        Member member = authService.getMemberFromAuthorizationHeader(authorizeHeader);

        Product product = productService.findById(id);

        if (!member.getRole().getValue().equals("ROLE_ADMIN")) {
            throw new ProductException.Forbidden("상품 수정 권한이 없습니다.");
        }

        productService.updateProduct(product, productRequestDto);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStockQuantity(product.getStockQuantity());
        productResponseDto.setStatus(product.getStatus());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setBrand(product.getBrand());
        productResponseDto.setThumbnailImageUrl(product.getThumbnailImageUrl());
        productResponseDto.setImageUrls(product.getImageUrls());
        productResponseDto.setDiscountRate(product.getDiscountRate());
        productResponseDto.setDiscountPrice(product.getDiscountPrice());
        productResponseDto.setNew(product.isNew());
        productResponseDto.setBest(product.isBest());
        productResponseDto.setDeleted(product.isDeleted());
        productResponseDto.setCreatedAt(product.getCreatedAt());
        productResponseDto.setUpdatedAt(product.getUpdatedAt());

        return ResponseEntity.ok(new RsData<>("200", "상품 수정이 정상적으로 완료되었습니다.", productResponseDto));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<RsData<Void>> deleteProduct(@PathVariable("id") Long id,
                                                      @RequestHeader("Authorization") String authorizeHeader) {

        Member member = authService.getMemberFromAuthorizationHeader(authorizeHeader);

        Product product = productService.findById(id);

        if (!member.getRole().getValue().equals("ROLE_ADMIN")) {
            throw new ProductException.Forbidden("상품 삭제 권한이 없습니다.");
        }

        productService.deleteProduct(product);

        return ResponseEntity.ok(new RsData<>("200", "상품 삭제가 정상적으로 완료되었습니다."));
    }
}
