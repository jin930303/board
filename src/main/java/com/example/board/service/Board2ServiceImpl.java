package com.example.board.service;

import com.example.board.entity.Board2Entity;
import com.example.board.repository.Board2Repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
@Getter
@NoArgsConstructor
public class Board2ServiceImpl implements Board2Service {

    @Autowired
    Board2Repository board2Repository;

    @Override
    public void insert(Board2Entity board2Entity) {
        board2Repository.save(board2Entity);
    }

    @Override
    public List<Board2Entity> out() {
        return board2Repository.findAll();
    }
}
