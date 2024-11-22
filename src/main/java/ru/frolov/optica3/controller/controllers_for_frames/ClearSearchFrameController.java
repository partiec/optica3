package ru.frolov.optica3.controller.controllers_for_frames;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.FiltersPayloadCache;
import ru.frolov.optica3.cache.PageCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.service.CacheService;
import ru.frolov.optica3.service.ModelService;


@Controller
@RequiredArgsConstructor
public class ClearSearchFrameController {

    private final ModelService modelService;
    private final CacheService cacheService;

    @PostMapping("api/clearSearch")
    public String postClearSearch(Model model) {

        System.out.println("___clearSearch();");

        // Кэшируем костыль filters, поскольку в полях не должно быть значений
        FiltersPayloadCache.setFiltersPayload(new FiltersPayload(null,null, null, null, null, null, null));

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = PageCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null);

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
