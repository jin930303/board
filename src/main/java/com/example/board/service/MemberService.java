package com.example.board.service;

import com.example.board.dto.MemberDTO;
import com.example.board.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService {
    boolean checkUsernameDuplication(String username);

    void save(MemberDTO memberDTO);

    boolean findNickname(String nickname);

    boolean findEmail(String email);

    Optional<MemberEntity> findByUsername(String username);
}
