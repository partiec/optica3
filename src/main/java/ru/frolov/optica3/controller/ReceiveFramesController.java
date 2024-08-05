package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.service.Defaults;
import ru.frolov.optica3.service.FrameService;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/receive")
public class ReceiveFramesController {
    /*
    ReceiveFramesController принимает crud и отдает представлению.
    Действия:
    - createdFrame
    - updatedFrame/{pageNumber:\d+}
    - deletedFrame/{pageNumber:\d+}
    - filteredFrames
 */

    private final FrameService frameService;


    @GetMapping("createdFrame")
    public String createdFrames(Model model) {
        fillModelAttrs(model, Defaults.PAGE_NUMBER, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);
        return "storage";
    }

    @GetMapping("updatedFrame/{pageNumber:\\d+}")
    public String updatedFrame(
            Model model,
            @PathVariable(name = "pageNumber", required = false) int pageNumber
    ) {

        if (pageNumber <= 0) {
            fillModelAttrs(model, Defaults.PAGE_NUMBER, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);
        } else {
            fillModelAttrs(model, pageNumber, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);
        }

        return "storage";
    }

    @GetMapping("deletedFrame/{pageNumber:\\d+}")
    public String deletedFrame(
            Model model,
            @PathVariable(name = "pageNumber", required = false) int pageNumber
    ) {

        if (pageNumber <= 0) {
            fillModelAttrs(model, Defaults.PAGE_NUMBER, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);
        } else {
            fillModelAttrs(model, pageNumber, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);
        }

        return "storage";
        //----------------->>>
    }

    @GetMapping("filteredFrames")
    public String filteredFrames(Model model) {

        fillModelAttrs(model, Defaults.PAGE_NUMBER, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);

        return "storage";
    }

    private void fillModelAttrs(Model model, int pageNumber, int pageSize, String sortField) {
        Page<Frame> page = this.frameService.getSortedPage(pageNumber, pageSize, sortField);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("frames", page.getContent());
        model.addAttribute("totalFrames", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }
}
