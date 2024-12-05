package ru.frolov.optica3.controller.glass_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.service.glass_services.*;

@Controller
@RequiredArgsConstructor
public class DeletePosition_GlassController {

    private final GlassContainerService containerService;
    private final GlassService glassService;
    private final Pagination_GlassService paginationService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/deleteGlassPosition")
    public String deleteGlassPosition(@RequestParam(name = "xId") Long xId,
                                 Model model) {

        // удаляем контейнер по xId
        this.containerService.deleteById(xId);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться с тем же номером и spec, но обновлена с учетом удаленного
        // pageNumber берем из кэша
        int pageNumber = Page_FrameCache.getPage().getNumber();

        // метод создаст page в зависимости от specStatus
        Page<GlassContainer> actualPage =
                paginationService.createPageDependsOnSpecStatus(pageNumber, null);

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                actualPage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна


        return "displayGlasses";
    }
}
