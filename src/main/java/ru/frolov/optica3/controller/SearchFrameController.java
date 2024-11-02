package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.spec.FrameSpec;


@Controller
public class SearchFrameController {

    private final FrameContainerService service;

    public SearchFrameController(FrameContainerService service) {
        this.service = service;
    }


    @PostMapping("api/searchFrames/{pageNumber:\\d+}")
    public String searchFrame(@PathVariable(name = "pageNumber", required = false) Integer pageNumber,
                              @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
                              FramePayload current,
                              RedirectAttributes ra) {

        if (pageNumber == null) pageNumber = 0;

        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        Specification<FrameContainer> spec = FrameSpec.allFieldsContains(
                current.firm(),
                current.model(),
                current.details(),
                current.purchase(),
                current.sale());
        Page<FrameContainer> page = this.service.getPage(spec, pageable); //!!!

        // заполняем модель для отправки в представление
        //-----------------------------------------------
        ra.addFlashAttribute("whichFieldOnInput", whichFieldOnInput);
        // текущие значения полей
        ra.addFlashAttribute("firm", current.firm());
        ra.addFlashAttribute("model", current.model());
        ra.addFlashAttribute("details", current.details());
        ra.addFlashAttribute("purchase", current.purchase());
        ra.addFlashAttribute("sale", current.sale());


        Cache.setPage(page);
        Cache.setPayload(current);


        return "redirect:/api/display";
    }


}
