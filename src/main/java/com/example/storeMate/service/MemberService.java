package com.example.storeMate.service;

import com.example.storeMate.base.repository.MemberRepository;
import com.example.storeMate.base.security.CustomUserDetailsService;
import com.example.storeMate.base.security.JwtProvider;
import com.example.storeMate.domain.dto.MemberDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final CustomUserDetailsService customUserDetailsService;


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

    public String login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        return jwtProvider.genToken(userDetails);
    }
}
