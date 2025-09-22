package com.example.storeMate.auth.controller;

import com.example.storeMate.auth.dto.RefreshTokenRequestDto;
import com.example.storeMate.auth.service.AuthService;
import com.example.storeMate.base.security.JwtProvider;
import com.example.storeMate.base.util.rsData.RsData;
import com.example.storeMate.domain.dto.LoginRequestDto;
import com.example.storeMate.domain.dto.LoginResponseDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<RsData<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {

        Member member = memberService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        String accessToken = jwtProvider.genToken(member);

        String refreshToken = authService.genRefreshToken(member);

        LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, refreshToken);

        return ResponseEntity.ok(new RsData<>("200", "로그인에 성공하였습니다.", loginResponseDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<RsData<Void>> logout(@RequestBody RefreshTokenRequestDto requestDto) {

        Member member = authService.findMemberByToken(requestDto.getRefreshToken());

        authService.logout(member);

        return ResponseEntity.ok(new RsData<>("200", "정상적으로 로그아웃하였습니다."));
    }
}
