package com.example.storeMate.domain.dto;

import lombok.Data;

@Data
public class MemberDto {

    private String username;

    private String password;

    private String passwordCnf;

    private String name;

    private String email;

}
