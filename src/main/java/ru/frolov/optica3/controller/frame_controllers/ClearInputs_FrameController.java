package ru.frolov.optica3.controller.frame_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.frame_cach.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_cach.Page_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.service.frame_services.Cache_FrameService;
import ru.frolov.optica3.service.frame_services.Model_FrameService;


@Controller
@RequiredArgsConstructor
public class ClearInputs_FrameController {

    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;

    @PostMapping("api/clearFrameInputs")
    public String clearFrameInputs(Model model) {

        System.out.println("___clearFrameInputs();");

        // Кэшируем костыль filters
        FiltersPayload_FrameCache.setFiltersFramePayload(new Filters_FramePayload(null, null, null, null, null, null, null, null));

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = Page_FrameCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelFrameService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheFrameService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayFrames";
    }
}
