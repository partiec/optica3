package ru.frolov.optica3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.service.Defaults;

@Controller
public class StartController {

    @GetMapping("api/start")
    public String start(){
        return "redirect:/api/arrange/pagedAndSortedFrames/%d/%s".formatted(1, Defaults.SORT_FIELD);
    }
}
