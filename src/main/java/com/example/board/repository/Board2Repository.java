package com.example.board.repository;

import com.example.board.entity.Board2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Board2Repository extends JpaRepository<Board2Entity ,Long> {
}
