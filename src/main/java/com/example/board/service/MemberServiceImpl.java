package com.example.board.service;

import com.example.board.dto.MemberDTO;
import com.example.board.entity.MemberEntity;
import com.example.board.repository.MemberRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public boolean findId(String id) {
        int count = memberRepository.countById(id);
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
    public void save(MemberDTO memberDTO) {

        MemberEntity entity = new MemberEntity();
        entity.setId(memberDTO.getId());
        entity.setPw(memberDTO.getPw());
        entity.setName(memberDTO.getName());
        entity.setEmail(memberDTO.getEmail());
        entity.setBirth(memberDTO.getBirth());
        entity.setNickname(memberDTO.getNickname());
        memberRepository.save(entity);
    }


}
