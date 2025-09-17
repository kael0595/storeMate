package com.example.storeMate.controller;

import com.example.storeMate.domain.dto.LoginRequestDto;
import com.example.storeMate.domain.dto.LoginResponseDto;
import com.example.storeMate.domain.dto.MemberDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberDto> register(@RequestBody MemberDto memberDto) {

        Member member = memberService.register(memberDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        String token = memberService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
