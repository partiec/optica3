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


@Controller
@RequiredArgsConstructor
public class Plus1_FrameController {

    private final FrameContainerService containerService;
    private final Cache_FrameService cacheFrameService;
    private final Pagination_FrameService paginationFrameService;
    private final Model_FrameService modelFrameService;


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/plus1Frame")
    public String plus1Frame(@RequestParam(name = "xId") Long xId,
                        Model model) {


        // Находим нужный контейнер по полученному id
        final FrameContainer xContainer = this.containerService.findById(xId).orElse(null);
        Frame newFrame = null;

        if (xContainer != null) {
            // создаем новую оправу с такими же значениями полей, как у контейнера
            newFrame = new Frame();
            newFrame.setFirm(xContainer.getFirm());
            newFrame.setModel(xContainer.getModel());
            ///////////////////////////////////////////////
            newFrame.setUseType(xContainer.getUseType());
            newFrame.setInstallType(xContainer.getInstallType());
            newFrame.setMaterial(xContainer.getMaterial());
            ///////////////////////////////////////////////
            newFrame.setDetails(xContainer.getDetails());
            newFrame.setPurchase(xContainer.getPurchase());
            newFrame.setSale(xContainer.getSale());

            // Соединяем xContainer и newFrame
            newFrame.setFrameContainer(xContainer);
            xContainer.addToFrameList(newFrame);
        }
        // сохраняем контейнер
        this.containerService.save(xContainer);


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться того же номера и spec, но обновлена с учетом добавления
        int pageNumber = Page_FrameCache.getPage().getNumber();
        Page<FrameContainer> actualPage = this.paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправка данных в модель
        // ------------------------>
        modelFrameService.transferModel(
                model,
                actualPage,
                null,
                xId,
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
}
