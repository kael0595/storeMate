package com.example.storeMate.auth.repository;

import com.example.storeMate.auth.entity.RefreshToken;
import com.example.storeMate.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByMember(Member member);
}
