package com.example.board.service;

import com.example.board.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    boolean checkUsernameDuplication(String username);

    void save(MemberDTO memberDTO);

    boolean findNickname(String nickname);

    boolean findEmail(String email);
}
