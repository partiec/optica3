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

    // при createFrame нужно:
    //      filters - нужны. По ним создается новый контейнер
    // при deletePosition нужно:
    //
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

        // вычислить сколько ВСЕГО ОПРАВ в БД, перебрав все контейнеры и сосчитав оправы в каждом
        long totalFrames = 0;
        for (FrameContainer c : this.containerService.all()) {
            totalFrames += c.getFrameList().size();
        }

        // список всех контейнеров (потом переберем для определения количества единиц товара при bySpec)
        List<FrameContainer> containers = new ArrayList<>();

        FiltersPayload filters = Cache.getFiltersPayload();

        // единиц товара
        long frames = 0;

        if (specRequirement.equals("noSpec") || !Cache.isSpecWasUsed()) {

            page = this.containerService.getPage(pageable);

            // нашлись ВСЕ КОНТЕЙНЕРЫ И ВСЕ ОПРАВЫ
            frames = totalFrames;
        }
        if (specRequirement.equals("bySpec") || Cache.isSpecWasUsed()) {

            spec = FrameSpec.byAllFieldsContains(filters.firm(), filters.model(), filters.details(), filters.purchase(), filters.sale());
            page = this.containerService.getPage(
                    spec,
                    pageable);
            // нашлись НЕ ВСЕ КОНТЕЙНЕРЫ
            containers = this.containerService.allBySpec(spec);

            // вычислить сколько оправ, перебрав найденные контейнеры и сосчитав оправы в каждом
            for (FrameContainer c : containers) {
                frames += c.getFrameList().size();
            }
        }

        model.addAttribute("page", page);
        model.addAttribute("frames", frames);
        model.addAttribute("totalFrames", totalFrames);
        model.addAttribute("filters", filters);


        return "displayFrames";
    }
}
