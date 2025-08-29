package com.example.board.service;

import com.example.board.dto.Board2DTO;
import com.example.board.entity.Board2Entity;
import com.example.board.repository.Board2Repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Setter
@Getter
@NoArgsConstructor
public class Board2ServiceImpl implements Board2Service {

    @Autowired
    Board2Repository board2Repository;

    @Override
    public void insert(Board2Entity board2Entity) {
        board2Repository.save(board2Entity);
    }

    @Override
    public List<Board2Entity> out() {
        return board2Repository.findAll();
    }

    @Override
    public Board2Entity findById(Long id) {
        return  board2Repository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 ID의 게시글이 없습니다."));
    }

    @Override
    public Board2Entity updateFindById(Long id) {
        return board2Repository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 ID의 게시글이 없습니다."));
    }

    @Override
    public void updateById(long id, String title, String content, LocalDateTime inputDate) {
        board2Repository.updateById(id,title,content,inputDate);
    }

    @Override
    public void deleteByid(Long id) {
        board2Repository.deleteById(id);
    }

    @Override
    public Page<Board2DTO> getBoardList(int page) {

        int pageNum = (page ==0)?0: page-1;
        int pageSize = 10;

        int startRow = pageNum * pageSize +1;
        int endRow = startRow + pageSize -1;

        System.out.println("시작 :" +startRow);
        System.out.println("종료 :" +endRow);

        List<Board2Entity> entityList = board2Repository.findByPaging(startRow,endRow);

        long totalCount = board2Repository.countBoard2();

        List<Board2DTO> dtoList = entityList.stream()
                                            .map(Board2DTO::new)
                                            .toList();
        Pageable pageable =PageRequest.of(pageNum,pageSize);
        return new PageImpl<>(dtoList,pageable,totalCount);
    }


}
