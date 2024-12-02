package ru.frolov.optica3.controller.frame_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.frame_services.*;

@Controller
@RequiredArgsConstructor
public class DeletePosition_FrameController {

    private final FrameContainerService containerService;
    private final FrameService frameService;
    private final Pagination_FrameService paginationFrameService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;


// Проблема: Наша задача удалить контейнер, а потом отобразить ту же таблицу, но с учетом удаления. При удалении
// текущее состояние таблицы может быть и allFrames, и bySpec. Метод display() должен знать - каково текущее состояние.
//
//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/deleteFramePosition")
    public String deletePosition(@RequestParam(name = "xId") Long xId,
                                 Model model) {

        System.out.println("___deleteFrame();");

        // удаляем контейнер по xId
        this.containerService.deleteById(xId);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться с тем же номером и spec, но обновлена с учетом удаленного
        // pageNumber берем из кэша
        int pageNumber = Page_FrameCache.getPage().getNumber();

        // метод создаст page в зависимости от specStatus
        Page<FrameContainer> actualPage =
                paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправляем данные в модель
        // --------------------------->
        modelFrameService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheFrameService.cacheAttributes(
                actualPage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна


        return "displayFrames";
    }
}
