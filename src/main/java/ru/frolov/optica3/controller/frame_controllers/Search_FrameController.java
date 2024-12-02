package ru.frolov.optica3.controller.frame_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.frame_caches.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_caches.SpecStatus_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.service.frame_services.Cache_FrameService;
import ru.frolov.optica3.service.frame_services.FrameContainerService;
import ru.frolov.optica3.service.frame_services.Model_FrameService;
import ru.frolov.optica3.service.frame_services.Pagination_FrameService;


@Controller
@RequiredArgsConstructor
public class Search_FrameController {

    private final FrameContainerService containerService;
    private final Pagination_FrameService paginationFrameService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;
//-------------------------------------------------------------------------------------------


    @PostMapping("api/searchFrames")
    public String searchFrame(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            Filters_FramePayload filters,
            Model model) {

        System.out.println("___searchFrame(); filters(" +
                filters.firm() + "," +
                filters.model() + "," +
                ////////////////////////////////////////
                filters.useType() + "," +
                filters.installType() + "," +
                filters.material() + "," +
                ////////////////////////////////////////
                filters.details() + "," +
                filters.purchase() + "," +
                filters.sale() + ");");

        // Подготовка данных для модели
        // --------------------------->
        // page однозначно должна быть bySpec
        SpecStatus_FrameCache.setApplied(true);
        FiltersPayload_FrameCache.setFiltersFramePayload(filters);
        Page<FrameContainer> actualPage = this.paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(0);


        // Отправка данных в модель
        // ------------------------->
        modelFrameService.transferModel(
                model,                      // model___
                actualPage,
                null,                       // (pageNumberForFlip) - здесь не нужен
                null,                       // (xId) - здесь не нужен
                whichFieldOnInput);         // (whichFieldOnInputForSearch) - нужен для фокуса в html

        // Контрольное кэширование
        cacheFrameService.cacheAttributes(
                actualPage,
                null,
                filters,
                null);


        return "displayFrames";
    }


}
