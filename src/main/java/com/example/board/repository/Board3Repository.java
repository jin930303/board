package com.example.board.repository;

import com.example.board.entity.Board3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Board3Repository extends JpaRepository<Board3Entity,Long> {


    @Query(value ="select * from (select a.* ,row_number() over(order by a.id desc) as rnum from board3 a)" +
                  "where rnum between :startRow and :endRow",nativeQuery = true )
    List<Board3Entity> findByPage(@Param("startRow") int startRow,@Param("endRow") int endRow);

    @Query(value = "select count(*) from board3",nativeQuery = true)
    long countBoard3();

    @Query(value = "select * from (select a.* ,row_number() over(order by a.id desc) as rnum from board3 a " +
                   "where a.title like %:kw% or a.content like %:kw%)" +
                    "where rnum between :startRow and :endRow",nativeQuery = true)
    List<Board3Entity> findByPageAndKeyword(@Param("startRow") int startRow,
                                                @Param("endRow") int endRow, @Param("kw") String kw);

    @Query(value = "select count(*) from board3 a where a.title like %:kw% or a.content like %:kw%",nativeQuery = true)
    long countBoard3WithKeyword(@Param("kw") String kw);
}
