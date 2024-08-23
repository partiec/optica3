package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.service.FrameService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/filtering")
public class FilterController {

    private final FrameService frameService;
    private String filterCopy;


    @GetMapping("filteredFrames/{pageNumberF:\\d+}")
    public String filteredFrames(
            Model model,
            @PathVariable(name = "pageNumberF", required = false) Integer pageNumberF,
            @RequestParam(name = "filter", required = false) String filter
    ) {

        if (filter != null) {
            this.filterCopy = filter;
        }

        System.out.println("--------------filter = " + filter);
        System.out.println("--------------filterCopy = " + filterCopy);
        List<Frame> totalResult = this.frameService.filterResult(filterCopy);
        System.out.println("---total.size = " + totalResult.size());
        List<Frame> pageContentF = this.frameService.getPageFiltered(totalResult, pageNumberF, 10);
        System.out.println("---pageContent.size = " + pageContentF.size());
        int totalPagesF = (totalResult.size() / 10) + (totalResult.size() % 10 == 0 ? 0 : 1);
        System.out.println("---totalPages = " + totalPagesF);

        //model.addFlashAttribute("errors", from flash);
        model.addAttribute("pageContentF", pageContentF);
        model.addAttribute("pageNumberF", pageNumberF);
        model.addAttribute("totalElementsF", totalResult);
        model.addAttribute("totalElementsFSize", totalResult.size());
        model.addAttribute("totalPagesF", totalPagesF);
        model.addAttribute("filter", this.filterCopy);
        System.out.println("----------------------------------------------------------------------------");

        return "filtered";
    }

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${pageContentF} -->
    // ${pageNumberF} -->
    // ${totalElementsF} -->
    // ${totalPagesF} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->
}
