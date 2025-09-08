package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "member")
@SequenceGenerator(name = "id_seq",sequenceName = "id_seq",initialValue = 1,allocationSize = 1)
public class MemberEntity {

    @Id
    @Column
    @GeneratedValue(generator = "id_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String pw;

    @Column
    private String realname;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private LocalDate birth;

    @Column
    private String role;
}
