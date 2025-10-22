package com.example.storeMate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> content;

    private int size;

    private int page;

    private long totalElements;

    private int totalPages;
}
