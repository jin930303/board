package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
public class Board5DTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime inputDate;
    private String author;
    private MultipartFile img;
    private String fileOriginalName;
    private String filePath;
    private Long fileSize;
    private int views;
    private int likes;
}

