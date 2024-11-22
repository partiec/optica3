package ru.frolov.optica3.controller.controllers_for_frames;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.CacheService;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.ModelService;
import ru.frolov.optica3.service.PaginationService;
import ru.frolov.optica3.cache.PageCache;

@Controller
@RequiredArgsConstructor
public class UpdateFrameController {


    private final FrameContainerService containerService;
    private final PaginationService paginationService;
    private final ModelService modelService;
    private final CacheService cacheService;


    //------------------------------------------------------------

    @PostMapping("api/updateFrame")
    public String updateFrame(@RequestParam(name = "xId") Long xId,
                              FramePayload framePayload,
                              Model model) {

        System.out.println("___updateFrame(); framePayload(" +
                framePayload.firm() + "," +
                framePayload.model() + "," +
                framePayload.frameInstallType() + "," +
                framePayload.frameMaterial() + "," +
                framePayload.details() + "," +
                framePayload.purchase() + "," +
                framePayload.sale() + ");");

        // находим нужный контейнер по xId
        FrameContainer xContainer = this.containerService.findById(xId).get();


        // если какая-то из переменных не null, то устанавливаем ее значение в контейнер
        if (framePayload.firm() != null) {
            xContainer.setFirm(framePayload.firm());
        }
        if (framePayload.model() != null) {
            xContainer.setModel(framePayload.model());
        }
        if (framePayload.frameInstallType() != null) {
            xContainer.setFrameInstallType(framePayload.frameInstallType());
        }
        if (framePayload.frameMaterial() != null) {
            xContainer.setFrameMaterial(framePayload.frameMaterial());
        }
        if (framePayload.details() != null) {
            xContainer.setDetails(framePayload.details());
        }
        if (framePayload.purchase() != null) {
            xContainer.setPurchase(framePayload.purchase());
        }
        if (framePayload.sale() != null) {
            xContainer.setSale(framePayload.sale());
        }

        // сохраняем измененных контейнер
        this.containerService.save(xContainer);


        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться с тем же номером и spec, но обновлена (для info html)
        int pageNumber = PageCache.getPage().getNumber();
        Page<FrameContainer> actualPage = this.paginationService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                actualPage,
                pageNumber,
                xId,
                null);

        // Контрольное кэширование
        // ------------------------->
        cacheService.cacheAttributes(
                actualPage,
                null,
                null,
                framePayload);


        return "displayFrames";
    }
}
