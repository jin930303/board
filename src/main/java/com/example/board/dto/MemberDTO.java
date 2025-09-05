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

    private String id;
    private String pw;
    private String name;
    private String nickname;
    private String email;
    private LocalDate birth;

    public MemberDTO(MemberEntity memberEntity){
        this.id=memberEntity.getId();
        this.pw=memberEntity.getPw();
        this.name=memberEntity.getName();
        this.nickname=memberEntity.getNickname();
        this.email=memberEntity.getEmail();
        this.birth=memberEntity.getBirth();
    }
}
