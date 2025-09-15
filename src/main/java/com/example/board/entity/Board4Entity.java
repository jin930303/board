package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "board4")
@SequenceGenerator(name = "board4_seq",sequenceName = "board4_seq",allocationSize = 1,initialValue = 1)
public class Board4Entity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "board4_seq",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    @Lob
    private String content;
    private String author;
    @Column(name = "INPUTDATE")
    private LocalDateTime inputDate;
    @Column(name = "FILEPATH")
    private String filePath;
    @Column(name = "FILEORIGINALNAME")
    private String fileOriginalName;
    @Column(name = "FILESIZE")
    private Long fileSize;

}
