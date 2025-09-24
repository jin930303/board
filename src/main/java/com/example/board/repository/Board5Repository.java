package com.example.board.repository;

import com.example.board.entity.Board5Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Board5Repository extends JpaRepository<Board5Entity, Long> {

    @Query(value = "select * from (select rownum as rn, b.* from board5 b order by b.board_id desc)" +
            " where rn between :startRow and :endRow", nativeQuery = true)
    List<Board5Entity> findByPagination(@Param("startRow") int startRow, @Param("endRow") int endRow);

    @Query(value = "select count(*) from board5", nativeQuery = true)
    long countBoards();

    @Query(value = "select * from (select rownum as rn, b.* from board5 b where b.title like %:kw% or " +
            "b.content like %:kw% order by b.board_id desc)" +
            "where rn between :startRow and :endRow",nativeQuery = true)
    List<Board5Entity> findByPageAndKeyWord(@Param("startRow") int startRow, int endRow, String kw);

    @Query(value = "select count(*) from board5 where title like %:kw% or content like %:kw%",nativeQuery = true)
    long countSearchBoards(String kw);
}
