package ru.frolov.optica3.controller.glass_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.*;


@Controller
@RequiredArgsConstructor
public class Start_GlassController {

    private final GlassContainerService containerService;
    private final Pagination_GlassService paginationService;
    private final GlassService glassService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;

    private final NoSpec_GlassController noSpecController;
    //-----------------------------------------------------------------------------------------------

    @GetMapping("api/startGlass")
    public String startGlass(Model model) {

        System.out.println("___startGlass();");

        // Подготовка кэша при старте
        // ---------------------------->
        // в качестве начальных данных кэшируем костыль filters:
        cacheService.cacheAttributes(
                null,
                null,
                new Filters_GlassPayload(null,null, null, null, null, null, null),
                null);



        return noSpecController.noSpecGlasses(model);
    }

    @PostMapping("api/startGlass")
    public String startPostGlass(Model model) {

        startGlass(model);

        return "displayGlasses";
    }
}
