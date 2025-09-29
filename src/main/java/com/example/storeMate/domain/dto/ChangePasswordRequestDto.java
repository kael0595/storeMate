package com.example.storeMate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {

    private String currentPassword;

    private String newPassword;

    private String newPasswordCnf;
}

