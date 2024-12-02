package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.service.glass_services.*;

@Controller
@RequiredArgsConstructor
public class FlipPages_GlassController {

    private final Pagination_GlassService paginationService;
    private final Cache_GlassService cacheService;
    private final Model_GlassService modelService;
    private final GlassContainerService containerService;
    private final GlassService glassService;

    @GetMapping("api/flipGlassPage/{pageNumber:\\d+}")
    public String flipGlassPage(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            Model model) {


        // Подготовка данных для модели
        // ------------------------------>
        // page должна быть с другим pageNumber, но specStatus тот же
        // метод использует spеcStatus из кэша и создаст page с номером из @PathVariable
        Page<GlassContainer>actualPage = paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправка данных в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                pageNumber,
                null,
                null);

        // Контрольное кэширование
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayGlasses";
    }
}
