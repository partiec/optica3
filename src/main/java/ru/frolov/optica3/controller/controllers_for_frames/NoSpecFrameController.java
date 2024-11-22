package ru.frolov.optica3.controller.controllers_for_frames;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.SpecStatusCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.*;


@Controller
@RequiredArgsConstructor
public class NoSpecFrameController {

    private final FrameContainerService containerService;
    private final PaginationService paginationService;
    private final FrameService frameService;
    private final ModelService modelService;
    private final CacheService cacheService;

    @GetMapping("api/noSpec")
    public String noSpec(Model model) {

        System.out.println("___noSpec();");

        // Подготовка данных для модели
        // ---------------------------->
        // кэшируем specStatus(false):
        SpecStatusCache.setApplied(false);
        // создаем page noSpec № 0
        Page<FrameContainer>actualPage = paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(0);


        // Отправка данных в модель
        modelService.transferModel(
                model,
                actualPage,
                0,
                null,
                 null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayFrames";
    }

    @PostMapping("api/noSpec")
    public String startPostRequest(Model model) {

        noSpec(model);

        return "displayFrames";
    }
}
