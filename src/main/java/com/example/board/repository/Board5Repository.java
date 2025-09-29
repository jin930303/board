package com.example.board.repository;

import com.example.board.entity.Board5Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Board5Repository extends JpaRepository<Board5Entity, Long> {

    @Query(value = "SELECT * FROM ("
            + "  SELECT ROWNUM AS rn, ordered_board.* FROM ("
            + "    SELECT b.* FROM board5 b ORDER BY b.board_id DESC"
            + "  ) ordered_board"
            + ") WHERE rn BETWEEN :startRow AND :endRow", nativeQuery = true)
    List<Board5Entity> findByPagination(@Param("startRow") int startRow, @Param("endRow") int endRow);

    @Query(value = "select count(*) from board5", nativeQuery = true)
    long countBoards();

    @Query(value = "SELECT * FROM ("
            + "  SELECT ROWNUM AS rn, final_ordered_result.* FROM ("
            + "    SELECT filtered_board.* FROM ("
            + "      SELECT b.* FROM board5 b WHERE b.title LIKE %:kw% OR b.content LIKE %:kw%"
            + "    ) filtered_board"
            + "    ORDER BY filtered_board.board_id DESC" // 최종 정렬
            + "  ) final_ordered_result"
            + ") WHERE rn BETWEEN :startRow AND :endRow",nativeQuery = true)
    List<Board5Entity> findByPageAndKeyWord(@Param("startRow") int startRow, int endRow, String kw);

    @Query(value = "select count(*) from board5 where title like %:kw% or content like %:kw%",nativeQuery = true)
    long countSearchBoards(String kw);
}
