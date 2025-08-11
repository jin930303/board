package com.example.board.controller;

import com.example.board.dto.Board2DTO;
import com.example.board.dto.BoardDTO;
import com.example.board.entity.Board2Entity;
import com.example.board.entity.BoardEntity;
import com.example.board.service.Board2Service;
import com.example.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    Board2Service board2Service;


    @GetMapping(value = "board1")
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

    @GetMapping(value = "board2")
    public String board6(Model mo){
        List<Board2Entity> list = board2Service.out();
        mo.addAttribute("list",list);
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
        Timestamp tp = Timestamp.valueOf(ld);
        dto2.setInputDate(tp);

        Board2Entity board2Entity = dto2.board2Entity();
        board2Service.insert(board2Entity);

        return "redirect:board2";
    }
}
