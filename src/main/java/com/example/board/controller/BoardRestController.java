package com.example.board.controller;


import com.example.board.service.Board2Service;
import com.example.board.service.Board3Service;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BoardRestController {

    @Autowired
    BoardService boardService;

    @Autowired
    Board2Service board2Service;

    @Autowired
    Board3Service board3Service;


    @DeleteMapping(value = "deleteBoard/{BOARDID}")
    public String Board(@PathVariable("BOARDID")long BOARDID){
        boardService.delete(BOARDID);
        return "redirect:/board1";
    }

    @DeleteMapping(value = "delete2Board/{id}")
    public String Board2(@PathVariable("id") Long id){
        board2Service.deleteByid(id);
        return "redirect:/board2";
    }

    @DeleteMapping(value = "delete3Board/{id}")
    public String board3(@PathVariable("id") Long id) throws IOException {
        board3Service.DeleteById(id);
        return "redirect:/board3";
    }
}
