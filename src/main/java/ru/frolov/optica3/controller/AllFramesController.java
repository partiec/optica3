package ru.frolov.optica3.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.service.FrameContainerService;


@Controller

public class AllFramesController {

    private final FrameContainerService frameContainerService;


    public AllFramesController(FrameContainerService frameContainerService, ApplicationContext applicationContext) {
        this.frameContainerService = frameContainerService;
    }
//--------------------------------------------------------------------------------

    @GetMapping("api/allFrames/{pageNumber:\\d+}")
    public String allFrames(@PathVariable(value = "pageNumber", required = false) Integer pageNumber) {


        if (pageNumber == null) {
            pageNumber = 0;
        }

        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        Page<FrameContainer> page = this.frameContainerService.getPage(pageable); // добыли page

        Cache.setPage(page);

        System.out.println("___allFrames...  cashed page = this.frameContainerService.getPage(pageable);");

        return "redirect:/api/display";
    }

    @PostMapping("api/allFrames")
    public String allFramesPage0Clear(@PathVariable(value = "pageNumber", required = false) Integer pageNumber) {

        allFrames(0);

        return "redirect:/api/display";
    }
}

