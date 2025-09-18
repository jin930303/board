package com.example.board.service;

import com.example.board.dto.Board5DTO;
import com.example.board.entity.Board5Entity;

import java.util.List;

public interface Board5Service {
    void save(Board5DTO board5DTO);

    List<Board5Entity> out();

    Board5Entity findByIdForUpdate(Long id);

    void updateBoard(Long id, Board5DTO board5DTO);

    void deleteById(Long id);

    Board5Entity findById(Long id);

    int addLike(Long boardId, String userId);
}
