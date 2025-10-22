package com.example.storeMate.auth.service;

import com.example.storeMate.auth.entity.RefreshToken;
import com.example.storeMate.auth.repository.RefreshTokenRepository;
import com.example.storeMate.base.security.JwtProvider;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.repository.MemberRepository;
import com.example.storeMate.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    private final MemberService memberService;

    @Transactional
    public void logout(Member member) {
        refreshTokenRepository.deleteByMember(member);
    }

    public String genRefreshToken(Member member) {

        String tokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setMember(member);
        refreshToken.setRefreshToken(tokenValue);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);

        return tokenValue;
    }

    public Member findMemberByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("RefreshToken이 존재하지 않습니다."));
        return refreshToken.getMember();
    }

    public Member getMemberFromAuthorizationHeader(String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtProvider.getUsernameFromToken(token);

        return memberService.findByUsername(username);
    }
}
