package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Column(name = "inputdate")
    private Timestamp inputDate;

    @Column
    private String author;

    @Builder
    public Board2Entity (long id, String title, String content, Timestamp inputDate, String author){
        this.author=author;
        this.id=id;
        this.title=title;
        this.content=content;
        this.inputDate=inputDate;
    }
}
