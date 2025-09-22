package com.example.storeMate.controller;

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

    @PostMapping("/register")
    public ResponseEntity<RsData> register(@RequestBody MemberDto memberDto) {

        Member member = memberService.register(memberDto);

        return ResponseEntity.ok(new RsData("200", "회원가입이 완료되었습니다.", member));
    }
}
