package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "member")
public class MemberEntity {

    @Id
    @Column
    String id;

    @Column
    String pw;

    @Column
    String name;

    @Column
    String nickname;

    @Column
    String email;

    @Column
    LocalDate birth;

}
