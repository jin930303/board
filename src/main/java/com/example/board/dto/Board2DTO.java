package com.example.board.dto;

import com.example.board.entity.Board2Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
public class Board2DTO {
    private long id;
    private String title;
    private String content;
    private LocalDateTime inputDate;
    private String author;

    public Board2Entity board2Entity(){
        return Board2Entity.builder()
                .id(id)
                .title(title)
                .content(content)
                .inputDate(inputDate)
                .author(author)
                .build();
    }
}
