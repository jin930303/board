package com.example.board.controller;


import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardRestController {

    @Autowired
    BoardService boardService;


    @DeleteMapping(value = "deleteBoard/{BOARDID}")
    public String Board(@PathVariable("BOARDID")long BOARDID){
        boardService.delete(BOARDID);
        return "redirect:board1";
    }

}
