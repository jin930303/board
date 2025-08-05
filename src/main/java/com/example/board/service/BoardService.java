package com.example.board.service;

import com.example.board.entity.BoardEntity;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    void insert(BoardEntity dto);

    List<BoardEntity> out();

    BoardEntity findout(long boardId);
}
