package com.example.board.service;

import com.example.board.dto.Board4DTO;
import com.example.board.entity.Board4Entity;

import java.io.IOException;
import java.util.List;

public interface Board4Service {
    void board4Save(Board4DTO board4DTO);

    List<Board4Entity> out();

    Board4Entity findById(Long id);

    void updateBoard(Long id, Board4DTO board4DTO);

    void deleteById(Long id)throws IOException;
}
