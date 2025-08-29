package com.example.board.repository;

import com.example.board.entity.Board3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Board3Repository extends JpaRepository<Board3Entity,Long> {
}
