package ru.frolov.optica3.controller.frame_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.service.frame_services.*;


@Controller
@RequiredArgsConstructor
public class Start_FrameController {

    private final FrameContainerService containerService;
    private final Pagination_FrameService paginationFrameService;
    private final FrameService frameService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;

    private final NoSpec_FrameController noSpecFrameController;
    //-----------------------------------------------------------------------------------------------

    @GetMapping("api/startFrame")
    public String startFrame(Model model) {

        System.out.println("___startFrame();");

        // Подготовка кэша при старте
        // ---------------------------->
        // в качестве начальных данных кэшируем костыль filters:
        cacheFrameService.cacheAttributes(
                null,
                null,
                new Filters_FramePayload(null,null, null, null, null, null, null, null),
                null);



        return noSpecFrameController.noSpecFrames(model);
    }

    @PostMapping("api/startFrame")
    public String startPostFrame(Model model) {

        startFrame(model);

        return "displayFrames";
    }
}
