package ru.frolov.optica3.controller.frame_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.frame_caches.SpecStatus_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.frame_services.*;


@Controller
@RequiredArgsConstructor
public class NoSpec_FrameController {

    private final FrameContainerService containerService;
    private final Pagination_FrameService paginationFrameService;
    private final FrameService frameService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;

    @GetMapping("api/noSpecFrames")
    public String noSpecFrames(Model model) {

        // Подготовка данных для модели
        // ---------------------------->
        // кэшируем specStatus(false):
        SpecStatus_FrameCache.setApplied(false);
        // создаем page noSpec № 0
        Page<FrameContainer>actualPage = paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(0);


        // Отправка данных в модель
        modelFrameService.transferModel(
                model,
                actualPage,
                null,
                null,
                 null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheFrameService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayFrames";
    }

    @PostMapping("api/noSpecFrames")
    public String startPostRequest(Model model) {

        noSpecFrames(model);

        return "displayFrames";
    }
}
