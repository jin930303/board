package com.example.board.dto;

import com.example.board.entity.Board3Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Board3DTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime inputDate;
    private MultipartFile file;
    private String filePath;
    private String fileOriginalName;
    private Long fileSize;

    public Board3DTO(Board3Entity board3Entity){
        this.id=board3Entity.getId();
        this.title=board3Entity.getTitle();
        this.content=board3Entity.getContent();
        this.author=board3Entity.getAuthor();
        this.inputDate=board3Entity.getInputDate();
        this.filePath=board3Entity.getFilePath();
        this.fileSize=board3Entity.getFileSize();
        this.fileOriginalName=board3Entity.getFileOriginalName();
    }


}
