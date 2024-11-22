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


@Controller
@RequiredArgsConstructor
public class Plus1FrameController {

    private final FrameContainerService containerService;
    private final CacheService cacheService;
    private final PaginationService paginationService;
    private final ModelService modelService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/plus1")
    public String plus1(@RequestParam(name = "xId") Long xId,
                        Model model) {

        System.out.println("___plus1();");

        // Находим нужный контейнер по полученному id
        final FrameContainer xContainer = this.containerService.findById(xId).orElse(null);

        // создаем новую оправу с такими же значениями полей, как у контейнера
        Frame newFrame = new Frame();
        newFrame.setFirm(xContainer.getFirm());
        newFrame.setModel(xContainer.getModel());
        newFrame.setDetails(xContainer.getDetails());
        newFrame.setPurchase(xContainer.getPurchase());
        newFrame.setSale(xContainer.getSale());

        // Соединяем xContainer и newFrame
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохраняем контейнер
        this.containerService.save(xContainer);


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом добавления
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
