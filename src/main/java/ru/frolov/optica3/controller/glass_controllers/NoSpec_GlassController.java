package ru.frolov.optica3.controller.glass_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.glass_caches.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_caches.SpecStatus_GlassCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.*;


@Controller
@RequiredArgsConstructor
public class NoSpec_GlassController {

    private final GlassContainerService containerService;
    private final Pagination_GlassService paginationService;
    private final GlassService glassService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;

    @GetMapping("api/noSpecGlasses")
    public String noSpecGlasses(Model model) {

        System.out.println("___noSpecGlasses()...");

        // Подготовка данных для модели
        // ---------------------------->
        // кэшируем specStatus(false):
        SpecStatus_GlassCache.setApplied(false);
        // создаем page noSpec № 0
        Page<GlassContainer> actualPage = paginationService.createPageDependsOnSpecStatus(0, null);
        // кэшируем filters костыли
        FiltersPayload_GlassCache.setFiltersPayload(new Filters_GlassPayload(null, null, null, null, null, null, null, null));


        // Отправка данных в модель
        modelService.transferModel(
                model,
                actualPage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayGlasses";
    }

    @PostMapping("api/noSpecGlasses")
    public String startPostRequest(Model model) {

        noSpecGlasses(model);

        return "displayGlasses";
    }
}
