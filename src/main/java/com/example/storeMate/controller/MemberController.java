package com.example.storeMate.controller;

import com.example.storeMate.base.security.JwtProvider;
import com.example.storeMate.base.util.rsData.RsData;
import com.example.storeMate.domain.dto.MemberDto;
import com.example.storeMate.domain.entity.Member;
import com.example.storeMate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<RsData> register(@RequestBody MemberDto memberDto) {

        Member member = memberService.register(memberDto);

        return ResponseEntity.ok(new RsData("200", "회원가입이 완료되었습니다.", member));
    }

    @GetMapping("/me")
    public ResponseEntity<RsData<MemberDto>> me(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtProvider.getUsernameFromToken(token);

        Member member = memberService.findByUsername(username);

        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(member.getUsername());
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setRole(member.getRole().getValue());

        return ResponseEntity.ok(new RsData<>("200", "회원정보 조회에 성공하였습니다.", memberDto));
    }
}
