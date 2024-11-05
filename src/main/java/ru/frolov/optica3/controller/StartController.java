package ru.frolov.optica3.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.payload.FiltersPayload;


@Controller
public class StartController {

    @GetMapping("api/start")
    public String start() {

        System.out.println("---start()...");

        Cache.setFiltersPayload(new FiltersPayload(null, null, null, null, null));

        return "redirect:/api/display/noSpec/0";
    }

    @PostMapping("api/start")
    public String startPostRequest() {

        System.out.println("---startPostRequest()...");

        Cache.setFiltersPayload(new FiltersPayload(null, null, null, null, null));

        return "redirect:/api/display/noSpec/0";
    }
}
