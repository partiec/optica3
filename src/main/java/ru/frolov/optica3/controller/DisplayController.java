package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.spec.FrameSpec;

import java.util.ArrayList;
import java.util.List;

@Controller
@Setter
@RequiredArgsConstructor
public class DisplayController {

    private final FrameContainerService containerService;


    //-----------------------------------------------------------------------------------------


    @GetMapping("api/display/{specRequirement}/{pageNumber:\\d+}")
    public String display(
            @PathVariable(name = "specRequirement", required = false) String specRequirement,
            @PathVariable(name = "pageNumber", required = false) Integer pageNumber,
            Model model) {

        System.out.println("____________________display()...");
        System.out.println("/////////////////////////////specRequirement = " + specRequirement);
        System.out.println("/////////////////////////////pageNumber = " + pageNumber);

        // Need:
        Page<FrameContainer> page = null;
        Pageable pageable = PageRequest.of(
                pageNumber,
                Defaults.PAGE_SIZE,
                Sort.by(Sort.Direction.ASC, "firm"));
        Specification<FrameContainer> spec;
        // список всех контейнеров (потом переберем для определения количества единиц товара)
        List<FrameContainer> totalContainers = new ArrayList<>();
        // единиц товара
        long totalFrames = 0;
        FiltersPayload filters = Cache.getFiltersPayload();


        if (specRequirement.equals("bySpec") || Cache.isSpecWasUsed()) {

            spec = FrameSpec.byAllFieldsContains(filters.firm(), filters.model(), filters.details(), filters.purchase(), filters.sale());
            page = this.containerService.getPage(
                    spec,
                    pageable);
            totalContainers = this.containerService.allBySpec(spec);
        }
        else if (specRequirement.equals("noSpec") || !Cache.isSpecWasUsed()) {

            page = this.containerService.getPage(pageable);
            totalContainers = this.containerService.all();
        }

        // вычислить totalFrames, перебрав все контейнеры и сосчитав оправы в каждом
        for (FrameContainer container : totalContainers) {
            totalFrames += container.getFrameList().size();
        }


        model.addAttribute("page", page);
        model.addAttribute("totalFrames", totalFrames);
        model.addAttribute("filters", filters);


        return "displayFrames";
    }
}
