package com.example.board.service;

import com.example.board.dto.Board5DTO;
import com.example.board.entity.Board5Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Board5Service {
    void save(Board5DTO board5DTO);

    List<Board5Entity> out();

    Board5Entity findByIdForUpdate(Long id);

    void updateBoard(Long id, Board5DTO board5DTO);

    void deleteById(Long id);

    Board5Entity findById(Long id);

    int addLike(Long boardId, String userId);

    Page<Board5Entity> findAll(Pageable pageable);

    Page<Board5Entity> searchBoardList(String kw, Pageable pageable);
}
