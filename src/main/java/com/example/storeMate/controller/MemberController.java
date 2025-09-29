package com.example.storeMate.controller;

import com.example.storeMate.base.security.JwtProvider;
import com.example.storeMate.base.util.rsData.RsData;
import com.example.storeMate.domain.dto.MemberRequestDto;
import com.example.storeMate.domain.dto.MemberResponseDto;
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
    public ResponseEntity<RsData<MemberResponseDto>> register(@RequestBody MemberRequestDto memberRequestDto) {

        Member member = memberService.register(memberRequestDto);

        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setUsername(member.getUsername());
        responseDto.setName(member.getName());
        responseDto.setEmail(member.getEmail());
        responseDto.setRole(member.getRole().getValue());

        return ResponseEntity.ok(new RsData<>("200", "회원가입이 완료되었습니다.", responseDto));
    }

    @GetMapping("/me")
    public ResponseEntity<RsData<MemberResponseDto>> me(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtProvider.getUsernameFromToken(token);

        Member member = memberService.findByUsername(username);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setUsername(member.getUsername());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setRole(member.getRole().getValue());

        return ResponseEntity.ok(new RsData<>("200", "회원정보 조회에 성공하였습니다.", memberResponseDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RsData<Void>> deleteMember(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtProvider.getUsernameFromToken(token);

        Member member = memberService.findByUsername(username);

        memberService.deleteMember(member);

        return ResponseEntity.ok(new RsData<>("200", "회원탈퇴가 정상적으로 완료되었습니다."));
    }
}
