package ru.frolov.optica3.controller.products.glass_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.service.products.glasses.GlassContainerService;
import ru.frolov.optica3.service.products.glasses.GlassUnitService;


@Controller
public class Start_GlassController
        extends AbstractGlassController {

    private final NoSpec_GlassController noSpec;

    public Start_GlassController(GlassContainerService containerService, GlassUnitService unitService, NoSpec_GlassController noSpec) {
        super(containerService, unitService);
        this.noSpec = noSpec;
    }


    @GetMapping("start")
    public String start(Model model) {


        return noSpec.noSpec(model);
    }

    @PostMapping("start")
    public String startPost(Model model) {

        start(model);

        return "displayGlasses";
    }
}
