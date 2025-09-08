package com.example.board.dto;

import com.example.board.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String username;
    private String pw;
    private String realname;
    private String nickname;
    private String email;
    private LocalDate birth;
    private String role;

    public MemberDTO(MemberEntity memberEntity){
        this.id=memberEntity.getId();
        this.pw=memberEntity.getPw();
        this.username=memberEntity.getUsername();
        this.nickname=memberEntity.getNickname();
        this.realname=memberEntity.getRealname();
        this.email=memberEntity.getEmail();
        this.birth=memberEntity.getBirth();
        this.role=memberEntity.getRole();
    }
}
