package ru.frolov.optica3.controller.frame_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.frame_services.Cache_FrameService;
import ru.frolov.optica3.service.frame_services.FrameContainerService;
import ru.frolov.optica3.service.frame_services.Model_FrameService;
import ru.frolov.optica3.service.frame_services.Pagination_FrameService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Minus1_FrameController {

    private final FrameContainerService containerService;
    private final Pagination_FrameService paginationFrameService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/minus1Frame")
    public String minus1Frame(@RequestParam(name = "xId") Long xId,
                              Model model) {

        System.out.println("___minus1();");

        // Находим нужный контейнер по полученному id
        FrameContainer xContainer = this.containerService.findById(xId).get();

        // список единиц товара в xContainer
        List<Frame> xList = xContainer.getFrameList();

        // Если в контейнере осталась последняя оправа или ни одной, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только firstElement
            Frame firstElement = xContainer.getFrameList().get(0);
            xContainer.getFrameList().remove(firstElement);
            // сохранить контейнер в БД
            this.containerService.save(xContainer);
        }

        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом удаления
        int pageNumber = Page_FrameCache.getPage().getNumber();
        Page<FrameContainer> actualPage = this.paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправка данных в модель
        // ------------------------>
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
                null,
                null,
                null);

        return "displayFrames";
    }
}
