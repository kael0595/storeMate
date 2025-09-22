package com.example.storeMate.auth.service;

import com.example.storeMate.auth.entity.RefreshToken;
import com.example.storeMate.auth.repository.RefreshTokenRepository;
import com.example.storeMate.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void logout(Member member) {
        refreshTokenRepository.deleteByMember(member);
    }
}
