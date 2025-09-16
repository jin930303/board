package com.example.board.service;

import com.example.board.dto.Board3DTO;
import com.example.board.entity.Board3Entity;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface Board3Service {
    void saveBoard(Board3DTO board3DTO);

    List<Board3Entity> out();

    Board3Entity findById(Long id);

    void UpdateBoard(Board3DTO board3DTO, Long id);

    void DeleteById(Long id) throws IOException;

    Page<Board3DTO> getBoardList(int page);


    Page<Board3DTO> searchBoardList(String kw, int page);
}
