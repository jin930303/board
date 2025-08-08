package com.example.board.repository;

import com.example.board.entity.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    @Transactional
    @Modifying
    @Query(value = "update board1 set title = :title , content = :content where boardid = :boardid", nativeQuery = true)
    void updateById(@Param("boardid") Long boardid, @Param("content") String content,
                    @Param("title") String title);
}
