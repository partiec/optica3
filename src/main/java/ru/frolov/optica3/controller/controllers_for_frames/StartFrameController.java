package ru.frolov.optica3.controller.controllers_for_frames;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.service.*;


@Controller
@RequiredArgsConstructor
public class StartFrameController {

    private final FrameContainerService containerService;
    private final PaginationService paginationService;
    private final FrameService frameService;
    private final ModelService modelService;
    private final CacheService cacheService;

    private final NoSpecFrameController noSpecFrameController;
    //-----------------------------------------------------------------------------------------------

    @GetMapping("api/start")
    public String start(Model model) {

        System.out.println("___start();");

        // Подготовка кэша при старте
        // ---------------------------->
        // в качестве начальных данных кэшируем specStatus(false) и костыль для filters:
        cacheService.cacheAttributes(
                null,
                false,
                new FiltersPayload(null, null, null, null, null, null, null),
                null);

        noSpecFrameController.noSpec(model);

        return "displayFrames";
    }

    @PostMapping("api/start")
    public String startPostRequest(Model model) {

        start(model);

        return "displayFrames";
    }
}
