package ru.frolov.optica3.controller.products.frame_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;


@Controller
public class Start_FrameController
        extends AbstractFrameController {

    private final NoSpec_FrameController noSpecController;

    public Start_FrameController(FrameContainerService containerService, FrameUnitService unitService, NoSpec_FrameController noSpec) {
        super(containerService, unitService);
        this.noSpecController = noSpec;
    }

//--------------------------------------------------------------------

    @GetMapping("start")
    public String start(Model model) {


        return noSpecController.noSpec(model);
    }

    @PostMapping("start")
    public String startPostFrame(Model model) {

        start(model);

        return "displayFrames";
    }
}
