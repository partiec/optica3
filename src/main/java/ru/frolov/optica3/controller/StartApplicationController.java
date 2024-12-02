package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.controller.frame_controllers.Start_FrameController;

@Controller
@RequiredArgsConstructor
public class StartApplicationController {

    private final Start_FrameController startFrameController;


    @GetMapping("api/start")
    public String startApplication(Model model){
        return this.startFrameController.startFrame(model);
    }
}
