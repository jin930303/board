package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "board3")
@SequenceGenerator(name = "board3_seq" ,sequenceName = "board3_seq",initialValue = 1,allocationSize = 1)
public class Board3Entity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "board3_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "author",nullable = false)
    private String author;

    @Column(name = "filepath")
    private String filePath;

    @Column(name = "fileoriginalname")
    private String fileOriginalName;

    @Column(name = "filesize")
    private Long fileSize;

    @Column(name = "inputdate")
    private LocalDateTime inputDate;


}
