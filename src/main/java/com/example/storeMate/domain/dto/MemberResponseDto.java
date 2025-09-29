package com.example.storeMate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private String username;

    private String name;

    private String email;

    private String role;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}