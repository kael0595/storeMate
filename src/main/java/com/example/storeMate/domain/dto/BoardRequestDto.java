package com.example.storeMate.domain.dto;

import com.example.storeMate.domain.entity.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    private Long id;

    private String name;

    private String description;

    private BoardType boardType;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
