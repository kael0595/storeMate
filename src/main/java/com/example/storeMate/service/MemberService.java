package com.example.storeMate.service;

import com.example.storeMate.auth.repository.RefreshTokenRepository;
import com.example.storeMate.base.repository.MemberRepository;
import com.example.storeMate.domain.dto.MemberDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member register(MemberDto memberDto) {

        String rawPassword = passwordEncoder.encode(memberDto.getPassword());

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(rawPassword)
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        if (memberDto.getUsername().startsWith("admin")) {
            member.setRole(Role.ADMIN);
        } else {
            member.setRole(Role.USER);
        }

        return memberRepository.save(member);
    }

    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }

    public void deleteMember(Member member) {
        member.setDeleted(true);
        member.setDeletedAt(LocalDateTime.now());
        memberRepository.save(member);
    }
}
