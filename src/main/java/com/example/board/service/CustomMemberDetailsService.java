package com.example.board.service;

import com.example.board.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService{

    private final MemberService memberService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberService.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("해당 회원이 존재하지 않습니다."+username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getUsername())
                .password(member.getPw())
                .roles(member.getRole())
                .build();
    }
}
