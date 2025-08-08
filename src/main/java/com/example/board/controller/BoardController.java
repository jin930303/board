package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;


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

}
