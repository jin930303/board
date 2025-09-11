package com.example.board.service;

import com.example.board.dto.MemberDTO;
import com.example.board.entity.MemberEntity;
import com.example.board.repository.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean checkUsernameDuplication(String username) {
        int count = memberRepository.countByUsername(username);
        return count>0;
    }

    @Override
    public boolean findNickname(String nickname) {
        int count = memberRepository.countByNickname(nickname);

        return count>0;
    }

    @Override
    public boolean findEmail(String email) {
        int count = memberRepository.countByEmail(email);
        return count>0;
    }

    @Override
    public Optional<MemberEntity> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public void save(MemberDTO memberDTO) {

        MemberEntity entity = new MemberEntity();
        entity.setId(memberDTO.getId());
        entity.setUsername(memberDTO.getUsername());
        entity.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        entity.setRealname(memberDTO.getRealname());
        entity.setEmail(memberDTO.getEmail());
        entity.setBirth(memberDTO.getBirth());
        entity.setNickname(memberDTO.getNickname());
        entity.setRole(memberDTO.getRole());
        memberRepository.save(entity);
    }


}
