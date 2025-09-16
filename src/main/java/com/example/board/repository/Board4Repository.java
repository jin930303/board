package com.example.board.repository;

import com.example.board.entity.Board4Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Board4Repository extends JpaRepository<Board4Entity,Long> {

}
