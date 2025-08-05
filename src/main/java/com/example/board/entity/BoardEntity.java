package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="board1")
@SequenceGenerator(name = "boardseq",sequenceName ="boardseq",initialValue = 1,allocationSize = 1)
public class BoardEntity {

    @Id
    @Column(name="BOARDID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "boardseq")
    private long BOARDID;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String author;

    @Builder
    public BoardEntity(long BOARDID, String title, String content, String author){
        this.BOARDID=BOARDID;
        this.title=title;
        this.content=content;
        this.author=author;
    }
}
