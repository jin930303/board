package com.example.board.repository;

import com.example.board.entity.Board2Entity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface Board2Repository extends JpaRepository<Board2Entity ,Long> {

    @Transactional
    @Modifying
    @Query(value = "update board2 b set b.title=:title,b.content=:content, b.inputDate=:inputDate " +
            "where b.id=:id",nativeQuery = true)
    void updateById(@Param("id") long id, @Param("title")String title,@Param("content") String content,
                    @Param("inputDate")LocalDateTime inputDate);


    @Query(value = "select * from (select a.*,row_number() over(order by a.id desc) as rnum from board2 a )" +
                   "where rnum between :startRow and :endRow",nativeQuery = true)
    List<Board2Entity> findByPaging(@Param("startRow") int startRow, @Param("endRow") int endRow);


    @Query(value = "select count(*) from board2",nativeQuery = true)
    long countBoard2();
}
