package com.example.board.service;

import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Setter
@Getter
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository repository;

    @Override
    public void insert(BoardEntity entity) {
        repository.save(entity);
    }

    @Override
    public List<BoardEntity> out() {
        return repository.findAll();
    }

    @Override
    public BoardEntity findout(long boardId) {
        return repository.findById(boardId).orElse(null);
    }

    @Override
    public void delete(long boardId) {repository.deleteById(boardId);
    }

    @Override
    public void updateById( BoardEntity boardEntity) {
        repository.updateById(boardEntity.getBOARDID(),boardEntity.getContent(),boardEntity.getTitle());
    }


}
