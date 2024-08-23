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

import java.util.ArrayList;


@Controller
@RequiredArgsConstructor
@RequestMapping("api/arrange")
public class ArrangementFramesController {
/*
    ArrangementFramesController отвечает за распределение оправ в нужном виде.

    - justFrames (без пагинации и сортировки)
    - sortedFrames/{field}
    - pagedFrames/{pageNumber}/{pageSize}
    - pagedAndSortedFrames/{pageNumber}/{pageSize}/{sortField}

    - filteredFrames/{filter}

 */


    private final FrameService frameService;


    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // -------------------------------------------------------------------------------------------- -->

    // 4. ходовой!
    @GetMapping(value = "pagedAndSortedFrames/{pageNumber}/{sortField}")
    public String pagedAndSortedFrames(
            Model model,
            @PathVariable(name = "pageNumber") int pageNumber,
            @PathVariable(name = "sortField") String sortField
    ) {

            Page<Frame> sortedPage = this.frameService.getPageSortedBy(pageNumber, Defaults.PAGE_SIZE, sortField);
            ArrayList<Frame> frames = new ArrayList<>(sortedPage.getContent());

            //model.addAttribute("errors", from flash);
            model.addAttribute("frames", frames);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("totalFrames", sortedPage.getTotalElements());
            model.addAttribute("totalPages", sortedPage.getTotalPages());


        return "storage";
    }

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // Without paging and sorting.
    @GetMapping("justFrames")
    public String justFrames(Model model) {

        ArrayList<Frame> frames = (ArrayList<Frame>) this.frameService.getList();
        // Page -

        //model.addAttribute("errors", from flash);
        model.addAttribute("frames", frames);
        model.addAttribute("pageNumber", 1);
        model.addAttribute("totalFrames", frames.size());
        model.addAttribute("totalPages", 1);
        return "storage";
    }

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // Sorting without paging.
    @GetMapping("sortedFrames/{field}")
    public String sortedFrames(
            Model model,
            @PathVariable(name = "field") String field) {

        ArrayList<Frame> frames = (ArrayList<Frame>) this.frameService.getListSortedBy(field);

        //model.addAttribute("errors", from flash);
        model.addAttribute("frames", frames);
        model.addAttribute("pageNumber", 1);
        model.addAttribute("totalFrames", frames.size());
        model.addAttribute("totalPages", 1);

        return "storage";
    }

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // Paging without sorting.
    @GetMapping(value = "pagedFrames/{pageNumber}")
    public String pagedFrames(
            Model model,
            @PathVariable(name = "pageNumber") int pageNumber
    ) {

        Page<Frame> page = this.frameService.getPage(pageNumber, Defaults.PAGE_SIZE);
        ArrayList<Frame> frames = new ArrayList<>(page.getContent());

        //model.addAttribute("errors", -);
        model.addAttribute("frames", frames);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalFrames", page.getTotalPages());
        model.addAttribute("totalPages", page.getTotalPages());


        return "storage";
    }


    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->


}

