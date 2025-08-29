package com.example.board.controller;

import com.example.board.dto.Board2DTO;
import com.example.board.dto.BoardDTO;
import com.example.board.entity.Board2Entity;
import com.example.board.entity.BoardEntity;
import com.example.board.service.Board2Service;
import com.example.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    Board2Service board2Service;


    @GetMapping(value = "/board1")
    public String board1(Model mo){
        List<BoardEntity> list = boardService.out();
        mo.addAttribute("list",list);
        return "board1";
    }

    @GetMapping(value ="inputBoard" )
    public String board2(){

        return "inputBoard";
    }

    @PostMapping(value = "boardSave")
    public String board3(@RequestParam("title") String title, @RequestParam("author") String author,
                         @RequestParam("content") String content, BoardDTO dto){

        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setContent(content);
        BoardEntity bEntity = dto.boardEntity();
        boardService.insert(bEntity);

        return "redirect:board1";
    }

    @GetMapping(value = "updateBoard")
    public String board4(@RequestParam("id") Long boardId, Model mo){
        BoardEntity board = boardService.findout(boardId);
        mo.addAttribute("board",board);
        return"updateBoardView";
    }

    @PostMapping(value = "updateSave")
    public String board5(@RequestParam("boardid") Long boardid,@RequestParam("title") String title,
                         @RequestParam("content") String content,BoardDTO dto)
    {
        dto.setBOARDID(boardid);
        dto.setContent(content);
        dto.setTitle(title);
        BoardEntity entity = dto.boardEntity();
        boardService.updateById(entity);
        return "redirect:board1";
    }

    @GetMapping(value = "/board2")
    public String board6(Model mo,@RequestParam(defaultValue = "1")int page){
        Page<Board2DTO> boardList = board2Service.getBoardList(page);

        int currentPage = boardList.getNumber()+1;
        int totalPages = boardList.getTotalPages();
        //블록 페이징

        int pageSize=5;
        int startPage = ((currentPage-1) / pageSize) * pageSize +1;
        int endPage = Math.min(startPage + pageSize -1, totalPages);

        mo.addAttribute("boardList",boardList);
        mo.addAttribute("currentPage",currentPage); //현재 페이지
        mo.addAttribute("totalPages",totalPages);
        mo.addAttribute("startPage",startPage);
        mo.addAttribute("endPage",endPage);
        // 총 페이지
        return "board2";
    }

    @GetMapping(value = "inputBoard2")
    public String board7(){

        return "inputBoard2";
    }

    @PostMapping(value = "boardSave2")
    public String board8(@RequestParam("title") String title, @RequestParam("content") String content
                        , @RequestParam("author") String author, Board2DTO dto2){
        dto2.setAuthor(author);
        dto2.setContent(content);
        dto2.setTitle(title);
        LocalDateTime ld = LocalDateTime.now();
        dto2.setInputDate(ld);

        Board2Entity board2Entity = dto2.board2Entity();
        board2Service.insert(board2Entity);

        return "redirect:board2";
    }

    @GetMapping(value = "/detail/{id}")
    public String board9(@PathVariable("id") Long id, Model mo){
        Board2Entity data = board2Service.findById(id);
        mo.addAttribute("data",data);
        return "board2Detail";
    }

    @GetMapping(value = "/update/{id}")
    public String board10(@PathVariable("id") Long id, Model mo){
        Board2Entity entity =board2Service.updateFindById(id);
        mo.addAttribute("entity",entity);
        return "update2BoardView";
    }

    @PostMapping(value = "update2Save/{id}")
    public String board11(@PathVariable("id") Long id, Board2DTO dto){
        dto.setId(id);
        LocalDateTime now = LocalDateTime.now();
        dto.setInputDate(now);
        board2Service.updateById(dto.getId(),dto.getTitle(),dto.getContent(),dto.getInputDate());

        return "redirect:/board2";
    }




}
