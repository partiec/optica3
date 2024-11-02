package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;
import ru.frolov.optica3.spec.FrameSpec;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DisplayController {

    private final FrameContainerService containerService;
    private final FrameService frameService;


    public DisplayController(FrameContainerService containerService, FrameService frameService) {
        this.containerService = containerService;
        this.frameService = frameService;
    }
    //-----------------------------------------------------------------------------------------


    @GetMapping("api/display")
    public String display(Model model) {

        Page<FrameContainer> page;
        List<FrameContainer> content = new ArrayList<>();
        Specification<FrameContainer> spec;
        // единиц товара
        long totalFrames = 0;

        // номер страницы оставить тот же (берем из кэша)
        int beforePageNumber = Cache.getPage().getNumber();
        Pageable pageable = PageRequest.of(beforePageNumber, Defaults.PAGE_SIZE);

        FramePayload payload = Cache.getPayload();

        //если при создании текущей страницы была использована Specification...
        if (Cache.specWasUsed()) {

            spec = FrameSpec.allFieldsContains(payload.firm(), payload.model(), payload.details(), payload.purchase(), payload.sale());
            content = this.containerService.allBySpec(spec);

            // создаем обновленную page по такому же принципу, что и была
            page = this.containerService.getPage(FrameSpec.allFieldsContains(
                            payload.firm(),
                            payload.model(),
                            payload.details(),
                            payload.purchase(),
                            payload.sale()),
                    pageable);

        } else {
            content = this.containerService.all();
            // создаем обновленную page по такому же принципу, что и была
            page = this.containerService.getPage(pageable);
        }


        for (FrameContainer container : content) {
            totalFrames += container.getFrameList().size();
        }



        model.addAttribute("page", page);
        model.addAttribute("totalFrames", totalFrames);
        model.addAttribute("payload", Cache.getPayload());

        Cache.setPage(page);

        return "displayFrames";
    }
}
