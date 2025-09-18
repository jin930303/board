package com.example.board.repository;

import com.example.board.entity.LikesLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesLogRepository extends JpaRepository<LikesLogEntity,Long> {


    Optional<LikesLogEntity> findByBoardIdAndUserId(Long boardId, String userId);
}
