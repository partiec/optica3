package ru.frolov.optica3.controller.frame_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.frame_services.*;

@Controller
@RequiredArgsConstructor
public class Flip_FrameController {

    private final Pagination_FrameService paginationFrameService;
    private final Cache_FrameService cacheFrameService;
    private final Model_FrameService modelFrameService;
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
        Page<FrameContainer>actualPage = paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправка данных в модель
        // --------------------------->
        modelFrameService.transferModel(
                model,
                actualPage,
                pageNumber,
                null,
                null,
                null);

        // Контрольное кэширование
        cacheFrameService.cacheAttributes(
                actualPage,
                null,
                null,
                null);

        return "displayFrames";
    }
}
