package com.example.board.repository;

import com.example.board.entity.Board2Entity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface Board2Repository extends JpaRepository<Board2Entity ,Long> {

    @Transactional
    @Modifying
    @Query(value = "update board2 b set b.title=:title,b.content=:content, b.inputDate=:inputDate " +
            "where b.id=:id",nativeQuery = true)
    void updateById(@Param("id") long id, @Param("title")String title,@Param("content") String content,
                    @Param("inputDate")LocalDateTime inputDate);
}
