package ru.frolov.optica3.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("api/test")
public class TestController {


    @GetMapping
    public String meth2(Model model){


        return "test1";
    }
}
