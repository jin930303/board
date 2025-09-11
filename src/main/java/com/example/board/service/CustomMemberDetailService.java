package com.example.board.service;

import com.example.board.entity.MemberEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomMemberDetailService implements UserDetailsService {

    private final MemberService memberService;

    public CustomMemberDetailService(MemberService memberService) {
        this.memberService = memberService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            MemberEntity member = memberService.findByUsername(username)
                    .orElseThrow(()->new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."+username));
            return User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .roles("USER")
                    .build();
        }
}
