package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "board_comment")
@SequenceGenerator(name = "comment_seq",sequenceName = "comment_seq",allocationSize = 1,initialValue = 1)
public class BoardCommentEntity {
    @Id
    @Column(name = "commentid")
    @GeneratedValue(generator = "comment_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String content;

    @Column(name = "createtime")
    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private  Board5Entity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity author;


}
