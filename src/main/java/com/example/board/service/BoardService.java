package com.example.board.service;

import com.example.board.entity.Board5Entity;
import com.example.board.entity.BoardEntity;

import java.util.List;

public interface BoardService {

    void insert(BoardEntity dto);

    List<BoardEntity> out();

    BoardEntity findout(long boardId);

    void delete(long boardId);

    void updateById(BoardEntity boardEntity);


}
