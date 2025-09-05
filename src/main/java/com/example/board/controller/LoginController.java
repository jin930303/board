package com.example.board.controller;

import com.example.board.dto.MemberDTO;
import com.example.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    MemberService memberService;

    @GetMapping(value = "/join")
    public String join(){
        return "/join";
    }


    @PostMapping(value = "/checkId")
    @ResponseBody
    public String checkId(@RequestParam("id") String id){
        boolean exist = memberService.findId(id);
        if(exist){
            return "duplicate";
        }
        else{
            return "available";
        }
    }

    @PostMapping(value = "/checkNickname")
    @ResponseBody
    public String checkNickname(@RequestParam("nickname") String nickname){
        boolean exist = memberService.findNickname(nickname);
        if(exist){
            return "duplicate";
        }
        else{
            return "available";
        }
    }

    @PostMapping(value = "/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email){
        boolean exist = memberService.findEmail(email);
        if(exist){
            return "duplicate";
        }
        else{
            return "available";
        }
    }

    @PostMapping(value = "/memberSave")
    public String memberSave(@ModelAttribute MemberDTO memberDTO){

        memberService.save(memberDTO);

        return "redirect:/";
    }

}
