package ru.frolov.optica3.controller.products.accessory_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.service.products.accessories.AccessoryContainerService;
import ru.frolov.optica3.service.products.accessories.AccessoryUnitService;


@Controller
public class Start_AccessoryController
        extends AbstractAccessoryController {

    private final NoSpec_AccessoryController noSpec;

    public Start_AccessoryController(AccessoryContainerService containerService, AccessoryUnitService unitService, NoSpec_AccessoryController noSpec) {
        super(containerService, unitService);
        this.noSpec = noSpec;
    }
//--------------------------------------------------------------------

    @GetMapping("start")
    public String start(Model model) {


        return noSpec.noSpec(model);
    }

    @PostMapping("start")
    public String startPost(Model model) {

        start(model);

        return "displayAccessories";
    }
}
