package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.frolov.optica3.service.Defaults;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.service.FrameService;




@Controller
@RequiredArgsConstructor
@RequestMapping("api/arrange")
public class ArrangeFramesController {
/*
    ArrangeFramesController берет из БД распределенный нужным образом список оправ,
    помещает в модель и отдает представлению.
    - justFrames (без пагинации и сортировки)
    - sortedFrames/{field}
    - pagedFrames/{offset}/{size}
    - pagedAndSortedFrames/{offset}/{size}/{field}
    - filteredFrames
 */


    private final FrameService frameService;


    // 1.
    @GetMapping("justFrames")
    public String justFrames(Model model) {
        model.addAttribute(
                "frames",
                this.frameService.findAll());

        return "storage";
    }

    // 2.
    @GetMapping("sortedFrames/{field}")
    public String sortedFrames(
            Model model,
            @PathVariable(name = "field") String field) {

        model.addAttribute(
                "frames",
                this.frameService.sortBy(field));

        return "storage";
    }

    // 3.
    @GetMapping(value = "pagedFrames/{offset}/{size}")
    public String pagedFrames(
            Model model,
            @PathVariable(name = "pageNumber") int pageNumber,
            @PathVariable(name = "size") int size) {

        fillModelAttrs(
                model,
                pageNumber,
                size,
                Defaults.SORT_FIELD);

        return "storage";
    }

    // 4.
    @GetMapping(value = "pagedAndSortedFrames/{offset}/{size}/{field}")
    public String pagedAndSortedFrames(
            Model model,
            @PathVariable(name = "pageNumber") int pageNumber,
            @PathVariable(name = "size") int size,
            @PathVariable(name = "field") String field
    ) {

        fillModelAttrs(
                model,
                pageNumber,
                size,
                field);

        return "storage";
    }


    // 5.
    @GetMapping("filteredFrames")
    public String filteredFrames(
            Model model,
            @RequestParam(name = "filter", required = false) String filter) {

        if (filter != null
                && !filter.isEmpty()
                && !filter.isBlank()) {

            fillModelAttrs(
                    model,
                    Defaults.PAGE_NUMBER,
                    Defaults.PAGE_SIZE,
                    Defaults.SORT_FIELD);

            model.addAttribute(
                    "frames",
                    this.frameService.findAllByFilter(filter));
        }
        return "storage";
    }



    // help
    private void fillModelAttrs(Model model, int pageNumber, int pageSize, String sortField) {
        Page<Frame> page = this.frameService.getSortedPage(pageNumber, pageSize, sortField);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("frames", page.getContent());
        model.addAttribute("totalFrames", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }
}

