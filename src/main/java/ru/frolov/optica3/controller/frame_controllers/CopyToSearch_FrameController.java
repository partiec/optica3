package ru.frolov.optica3.controller.frame_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.frame_cach.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_cach.Page_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.service.frame_services.*;

@Controller
@RequiredArgsConstructor
public class CopyToSearch_FrameController {

    private final FrameContainerService containerService;
    private final FrameService glassService;
    private final Pagination_FrameService paginationService;
    private final Model_FrameService modelService;
    private final Cache_FrameService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/copyToSearchFrames")
    public String copyToSearchFrames(@RequestParam(name = "xId") Long xId,
                                 Model model) {

        // find xContainer
        FrameContainer xContainer = this.containerService.findById(xId).get();

        // set fields values to FiltersPayload and cache
        FiltersPayload_FrameCache.setFiltersFramePayload(new Filters_FramePayload(
                xContainer.getFirm(),
                xContainer.getModel(),
                xContainer.getUseType(),
                xContainer.getInstallType(),
                xContainer.getMaterial(),
                xContainer.getDetails(),
                xContainer.getPurchase(),
                xContainer.getSale()
        ));

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = Page_FrameCache.getPage();

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

        return "displayFrames";
    }
}
