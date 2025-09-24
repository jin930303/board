package com.example.board.repository;

import com.example.board.entity.BoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<BoardCommentEntity,Long> {
    List<BoardCommentEntity> findByBoardIdOrderByCreateTimeAsc(Long boardId);
}
