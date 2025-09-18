package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "board5")
@SequenceGenerator(name = "board5_seq",sequenceName = "board5_seq",initialValue = 1,allocationSize = 1)

public class Board5Entity {

    @Id
    @Column
    @GeneratedValue(generator = "board5_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    @Lob
    private String content;
    private String author;
    @Column(name = "inputdate")
    private LocalDateTime inputDate;
    @Column(name = "fileoriginalname")
    private String fileOriginalName;
    @Column(name = "filepath")
    private String filePath;
    @Column(name = "filesize")
    private Long fileSize;
    private int likes;
    private int views;
}
