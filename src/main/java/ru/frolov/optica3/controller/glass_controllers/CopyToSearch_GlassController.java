package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.glass_cach.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_cach.Page_GlassCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.*;

@Controller
@RequiredArgsConstructor
public class CopyToSearch_GlassController {

    private final GlassContainerService containerService;
    private final GlassService glassService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/copyToSearchGlasses")
    public String copyToSearch(@RequestParam(name = "xId") Long xId,
                                 Model model) {

        // find xContainer
        GlassContainer xContainer = this.containerService.findById(xId).get();

        // set fields values to FiltersPayload and cache
        FiltersPayload_GlassCache.setFiltersPayload(new Filters_GlassPayload(
                xContainer.getFirm(),
                xContainer.getMaterial(),
                xContainer.getDesign(),
                xContainer.getCoat(),
                xContainer.getDetails(),
                xContainer.getPurchase(),
                xContainer.getSale(),
                xContainer.getDioptre()
        ));

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<GlassContainer> samePage = Page_GlassCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                "copyToSearch");

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayGlasses";
    }
}
