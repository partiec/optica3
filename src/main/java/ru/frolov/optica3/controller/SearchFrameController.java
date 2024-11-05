package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;


@Controller
@RequiredArgsConstructor
public class SearchFrameController {

    private final FrameContainerService service;
//-------------------------------------------------------------------------------------------


    @PostMapping("api/searchFrames")
    public String searchFrame(
            // нужна для фокуса в НАЙТИ/СОЗДАТЬ
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            // нужна для отображения текущих значений в НАЙТИ/СОЗДАТЬ
            FiltersPayload filters,
            RedirectAttributes ra) {

        System.out.println("___searchFrame()...");

        Cache.setFiltersPayload(filters);
        ra.addFlashAttribute("whichFieldOnInput", whichFieldOnInput);


        return "redirect:/api/display/bySpec/0";
    }


}
