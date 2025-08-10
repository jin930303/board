package com.example.board.entity;

import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "board2")
@SequenceGenerator(name = "boardseq2",sequenceName = "boardseq2",initialValue = 1,allocationSize = 1)
public class Board2Entity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardseq2")
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Date inputDate;

    @Column
    private String author;

    @Builder
    public Board2Entity (long id,String title,String content,Date inputDate,String author){
        this.author=author;
        this.id=id;
        this.title=title;
        this.content=content;
        this.inputDate=inputDate;
    }
}
