package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {



    @GetMapping(value = "/")
    public String home(){

        return "main";
    }

    @GetMapping(value = "/login")
    public String login1(){
        return "login";
    }

}
