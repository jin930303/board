package com.example.board.service;

import com.example.board.entity.Board2Entity;

import java.util.List;

public interface Board2Service {
    void insert(Board2Entity board2Entity);

    List<Board2Entity> out();
}
