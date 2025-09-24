package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDTO {
    private String content;
    private String authorNickname;
    private LocalDateTime createTime;
    private Long boardId;
}
