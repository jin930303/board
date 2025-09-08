package com.example.board.service;

import com.example.board.dto.MemberDTO;
import com.example.board.entity.MemberEntity;
import com.example.board.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Setter
@Getter
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

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
        entity.setPw(memberDTO.getPw());
        entity.setRealname(memberDTO.getRealname());
        entity.setEmail(memberDTO.getEmail());
        entity.setBirth(memberDTO.getBirth());
        entity.setNickname(memberDTO.getNickname());
        entity.setRole(memberDTO.getRole());
        memberRepository.save(entity);
    }


}
