package ru.frolov.optica3.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("api/start")
public class StartController {

    @GetMapping
    public String start() {


        return "redirect:/api/allFrames/0";
    }
}
