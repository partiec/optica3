package ru.frolov.optica3.controller.controllers_for_frames;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.PageCache;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.CacheService;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.ModelService;
import ru.frolov.optica3.service.PaginationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Minus1FrameController {

    private final FrameContainerService containerService;
    private final PaginationService paginationService;
    private final ModelService modelService;
    private final CacheService cacheService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/minus1")
    public String minus1(@RequestParam(name = "xId") Long xId,
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
        int pageNumber = PageCache.getPage().getNumber();
        Page<FrameContainer> actualPage = this.paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправка данных в модель
        // ------------------------>
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
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
}
