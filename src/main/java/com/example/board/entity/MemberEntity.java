package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column
    private String realname;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private LocalDate birth;

    @ColumnDefault("role_user")
    private String role ;
}
