package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.frame_caches.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_caches.SpecStatus_FrameCache;
import ru.frolov.optica3.cache.glass_caches.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_caches.SpecStatus_GlassCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.GlassContainerService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;
import ru.frolov.optica3.service.glass_services.Pagination_GlassService;


@Controller
@RequiredArgsConstructor
public class Search_GlassController {

    private final GlassContainerService containerService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;
//-------------------------------------------------------------------------------------------


    @PostMapping("api/searchGlasses")
    public String searchGlasses(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            Filters_GlassPayload filters,
            Model model) {


        // Подготовка данных для модели
        // --------------------------->
        // page однозначно должна быть bySpec
        SpecStatus_GlassCache.setApplied(true);
        FiltersPayload_GlassCache.setFiltersPayload(filters);
        Page<GlassContainer> actualPage = this.paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(0);


        // Отправка данных в модель
        // ------------------------->
        modelService.transferModel(
                model,                      // model___
                actualPage,
                null,                       // (pageNumberForFlip) - здесь не нужен
                null,                       // (xId) - здесь не нужен
                whichFieldOnInput);         // (whichFieldOnInputForSearch) - нужен для фокуса в html

        // Контрольное кэширование
        cacheService.cacheAttributes(
                actualPage,
                null,
                filters,
                null);


        return "displayGlasses";
    }


}
