package ru.frolov.optica3.controller.controllers_for_frames;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.*;

@Controller
@RequiredArgsConstructor
public class FlipFramePagesController {

    private final PaginationService paginationService;
    private final CacheService cacheService;
    private final ModelService modelService;
    private final FrameContainerService containerService;
    private final FrameService frameService;

    @GetMapping("api/flipFramePage/{pageNumber:\\d+}")
    public String flipFramePage(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            Model model) {

        System.out.println("___deleteFramePage();");

        // Подготовка данных для модели
        // ------------------------------>
        // page должна быть с другим pageNumber, но specStatus тот же
        // метод использует spеcStatus из кэша и создаст page с номером из @PathVariable
        Page<FrameContainer>actualPage = paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправка данных в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                pageNumber,
                null,
                null);

        // Контрольное кэширование
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayFrames";
    }
}
