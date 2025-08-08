package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BoardDTO {
    private long BOARDID;
    private String title;
    private String content;
    private String author;

    public  BoardEntity boardEntity(){
        return BoardEntity.builder()
            .BOARDID(BOARDID)
            .title(title)
            .content(content)
            .author(author)
            .build();
    }
}
