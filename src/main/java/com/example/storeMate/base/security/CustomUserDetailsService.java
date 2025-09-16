package com.example.storeMate.base.security;

import com.example.storeMate.base.repository.MemberRepository;
import com.example.storeMate.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("등록된 회원이 아닙니다."));
        return new User(
                member.getUsername(),
                member.getPassword(),
                getAuthorities(member)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole()));
    }
}
