package ru.frolov.optica3.controller.controllers_for_frames;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.FiltersPayloadCache;
import ru.frolov.optica3.cache.SpecStatusCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.service.CacheService;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.ModelService;
import ru.frolov.optica3.service.PaginationService;


@Controller
@RequiredArgsConstructor
public class SearchFrameController {

    private final FrameContainerService containerService;
    private final PaginationService paginationService;
    private final ModelService modelService;
    private final CacheService cacheService;
//-------------------------------------------------------------------------------------------


    @PostMapping("api/searchFrames")
    public String searchFrame(
            // это поле нужно для фокуса в нужном input в html
            @ModelAttribute(name = "whichFieldOnInput") String whichFieldOnInput,
            FiltersPayload filters,
            Model model) {

        System.out.println("___searchFrame(); filters(" +
                filters.firm() + "," +
                filters.model() + "," +
                filters.frameInstallType() + "," +
                filters.frameMaterial() + "," +
                filters.details() + "," +
                filters.purchase() + "," +
                filters.sale() + ");");

        // Подготовка данных для модели
        // --------------------------->
        // page однозначно должна быть bySpec
        SpecStatusCache.setApplied(true);
        FiltersPayloadCache.setFiltersPayload(filters);
        Page<FrameContainer> actualPage = this.paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(0);


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


        return "displayFrames";
    }


}
