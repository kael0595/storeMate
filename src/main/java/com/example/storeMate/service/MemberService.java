package com.example.storeMate.service;

import com.example.storeMate.base.exception.MemberException;
import com.example.storeMate.repository.MemberRepository;
import com.example.storeMate.domain.dto.ChangePasswordRequestDto;
import com.example.storeMate.domain.dto.MemberRequestDto;
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

    public Member register(MemberRequestDto memberDto) {

        String rawPassword = passwordEncoder.encode(memberDto.getPassword());

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(rawPassword)
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        if (memberRepository.count() == 0) {
            member.setRole(Role.ADMIN);
        } else {
            member.setRole(Role.USER);
        }

        return memberRepository.save(member);
    }

    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new MemberException.NotFound("회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MemberException.WrongPassword("비밀번호가 일치하지 않습니다.");
        }

        if (member.isDeleted()) {
            throw new MemberException.Deleted("탈퇴한 회원입니다.");
        }

        return member;
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new MemberException.NotFound("존재하지 않는 회원입니다."));
    }

    public void deleteMember(Member member) {
        member.setDeleted(true);
        member.setDeletedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    public void changeProfile(Member member, MemberRequestDto memberRequestDto) {
        if (!memberRequestDto.getName().isBlank()) {
            member.setName(memberRequestDto.getName());
        }

        if (!memberRequestDto.getEmail().isBlank()) {
            member.setEmail(memberRequestDto.getEmail());
        }

        member.setUpdatedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto, Member member) {
        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), member.getPassword())) {
            throw new MemberException.WrongPassword("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getNewPasswordCnf())) {
            throw new RuntimeException("새 비밀번호와 확인용 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        memberRepository.save(member);
    }
}
