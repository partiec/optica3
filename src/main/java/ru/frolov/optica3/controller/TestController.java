package ru.frolov.optica3.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("api/test")
public class TestController {

    @GetMapping("meth1")
    public String meth1(RedirectAttributes ra) {

        // при перенаправлении флэш-атрибуты появляются в модели другого метода
        ra.addFlashAttribute("fuflo1", "valueForFuflo1");
        ra.addFlashAttribute("fuflo2", "valueForFuflo2");
        return "redirect:/api/test/meth2";
    }

    @GetMapping("meth2")
    public String meth2(Model model){
        System.out.println(model.getAttribute("fuflo1"));
        System.out.println(model.getAttribute("fuflo2"));


        return "test1";
    }
}
