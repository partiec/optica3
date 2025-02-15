package ru.frolov.optica3.controller.products.frame_controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;

@Controller
public class DeletePosition_FrameController
        extends AbstractFrameController {


    public DeletePosition_FrameController(FrameContainerService containerService, FrameUnitService unitService) {
        super(containerService, unitService);
    }

    @Transactional
    @PostMapping("deletePosition")
    public String deletePosition(@RequestParam(name = "xContainerId") Long xContainerId,
                                 Model model) {

        /*
         * Задача:
         *   Удалить container по xOrderId и отобразить страницу с учетом удаленного
         *
         * */

        // удаляем контейнер по xOrderId
        containerService.deleteById(xContainerId);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться с тем же номером и spec, но обновлена с учетом удаленного
        int pageNumber = containerService.getCache().getPage().getNumber();
        Specification<FrameContainer> specification = containerService.getCache().getSpec();

        // создать page в зависимости от specStatus
        Page<FrameContainer> actualPage;
        if (specification != null) {
            actualPage = containerService.getPageBYSpec(pageNumber, specification);
        } else {
            actualPage = containerService.getPageNOSpec(pageNumber);
        }

        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                actualPage,
                null,
                xContainerId,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                actualPage,
                null,
                null,
                null,
                null
        );


        return "displayFrames";
    }
}
