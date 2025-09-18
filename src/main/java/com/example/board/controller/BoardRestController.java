package com.example.board.controller;


import com.example.board.dto.Board5DTO;
import com.example.board.entity.Board5Entity;
import com.example.board.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
public class BoardRestController {

    @Autowired
    BoardService boardService;

    @Autowired
    Board2Service board2Service;

    @Autowired
    Board3Service board3Service;

    @Autowired
    Board4Service board4Service;

    @Autowired
    Board5Service board5Service;

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

    @DeleteMapping(value = "/delete4Board/{id}")
    public ResponseEntity<String> board4(@PathVariable("id")Long id) {
        try {
            board4Service.deleteById(id);
            return ResponseEntity.ok("success");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("error");
        }
    }

    @DeleteMapping(value = "/delete5Board/{id}")
    public ResponseEntity<String> board5(@PathVariable("id")Long id){
        try {
            board5Service.deleteById(id);
            return ResponseEntity.ok("success");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("error");

        }
    }

    @GetMapping(value ="/api/detail5Board/{id}" )
    public Board5Entity getBoardDetail(@PathVariable("id")Long id){
        Board5Entity entity = board5Service.findById(id);
        return entity;
    }
    @PostMapping(value = "/api/board5/like/{boardId}")
    public ResponseEntity<Integer> addLike(@PathVariable("boardId")Long boardId, Principal principal){

        if(principal==null){
            return ResponseEntity.status(401).body(null);
        }
        String userId = principal.getName();
        try{
            int updateLikes = board5Service.addLike(boardId,userId);
            return ResponseEntity.ok(updateLikes);
        }
        catch(IllegalArgumentException | NoSuchElementException e ){
            return ResponseEntity.badRequest().body(null);
        }
        catch(Exception e ){
            return ResponseEntity.status(500).body(null);
        }
    }
}
