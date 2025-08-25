package com.example.board.service;

import com.example.board.entity.Board2Entity;

import java.time.LocalDateTime;
import java.util.List;

public interface Board2Service {
    void insert(Board2Entity board2Entity);

    List<Board2Entity> out();

    Board2Entity findById(Long id);

    Board2Entity updateFindById(Long id);

    void updateById(long id, String title, String content, LocalDateTime inputDate);

    void deleteByid(Long id);
}
