package com.example.board.dto;

import com.example.board.entity.Board4Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
public class Board4DTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime inputDate;
    private MultipartFile img;
    private String filePath;
    private String fileOriginalName;
    private Long fileSize;

    public Board4DTO() {
    }

    public Board4DTO (Board4Entity board4Entity) {
        if (board4Entity != null) {
            this.id = board4Entity.getId();
            this.title = board4Entity.getTitle();
            this.content = board4Entity.getContent();
            this.author = board4Entity.getAuthor();
            this.inputDate = board4Entity.getInputDate();
            this.filePath = board4Entity.getFilePath();
            this.fileOriginalName = board4Entity.getFileOriginalName();
            this.fileSize = board4Entity.getFileSize();
        }
        else{
            throw new NullPointerException("엔터티가 비어있습니다.");
            }
        }
    }

